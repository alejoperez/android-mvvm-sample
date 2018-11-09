package com.mvvm.sample.login

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.view.View
import com.mvvm.sample.R
import com.mvvm.sample.base.BaseActivity
import com.mvvm.sample.extensions.getWhiteSpaceFilters
import com.mvvm.sample.livedata.DataResource
import com.mvvm.sample.livedata.Status
import com.mvvm.sample.main.MainActivity
import com.mvvm.sample.webservice.LoginResponse
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity

class LoginActivity : BaseActivity() {

    private val viewModel by lazy { obtainViewModel(LoginViewModel::class.java) }

    private val onLoginResponseObserver = Observer<DataResource<LoginResponse>> {
        if (it != null) {
            onLoginResponse(it)
        } else {
            onLoginFailure()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initViewModel()
        initView()
    }

    private fun initView() {
        etEmail.filters = getWhiteSpaceFilters()
        etPassword.filters = getWhiteSpaceFilters()

        btLogin.setOnClickListener {
            onLoginClicked()
        }
    }

    private fun initViewModel() {
        viewModel.loginResponse.observe(this, onLoginResponseObserver)
    }

    private fun onLoginClicked() {
        if (viewModel.isValidForm(getEmail(), getPassword())) {
            showProgress()
            viewModel.login(getEmail(), getPassword())
        } else {
            if (!viewModel.isValidEmail(getEmail())) {
                etEmail.error = getString(R.string.error_invalid_email)
            }
            if (!viewModel.isValidPassword(getPassword())) {
                etPassword.error = getString(R.string.error_empty_password)
            }
        }
    }

    private fun getEmail(): String = etEmail.text.toString()

    private fun getPassword(): String = etPassword.text.toString()


    private fun onLoginResponse(response: DataResource<LoginResponse>) {
        hideProgress()
        when (response.status) {
            Status.SUCCESS -> onLoginSuccess()
            Status.FAILURE -> onLoginFailure()
            Status.NETWORK_ERROR -> onNetworkError()
        }
    }

    private fun onLoginSuccess() {
        startActivity<MainActivity>()
        finishAffinity()
    }

    private fun onLoginFailure() {
        showAlert(R.string.error_invalid_credentials)
    }

    private fun showProgress() {
        btLogin.visibility = View.INVISIBLE
        progress.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        btLogin.visibility = View.VISIBLE
        progress.visibility = View.INVISIBLE
    }
}
