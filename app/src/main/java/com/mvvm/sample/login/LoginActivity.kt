package com.mvvm.sample.login

import android.os.Bundle
import android.view.View
import com.mvvm.sample.R
import com.mvvm.sample.base.BaseActivity
import com.mvvm.sample.extensions.getWhiteSpaceFilters
import com.mvvm.sample.livedata.EventObserver
import com.mvvm.sample.main.MainActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity

class LoginActivity : BaseActivity() {

    private val viewModel by lazy { obtainViewModel(LoginViewModel::class.java) }

    private val onLoginSuccessObserver = EventObserver<Unit> { onLoginSuccess() }

    private val onLoginFailureObserver = EventObserver<Unit> { onLoginFailure() }

    private val onNetworkErrorObserver = EventObserver<Unit> { onNetworkError() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initView()
        initViewModel()
    }

    private fun initView() {
        etEmail.filters = getWhiteSpaceFilters()
        etPassword.filters = getWhiteSpaceFilters()

        btLogin.setOnClickListener {
            onLoginClicked()
        }
    }

    private fun initViewModel() {
        viewModel.onLoginSuccess.observe(this, onLoginSuccessObserver)
        viewModel.onLoginFailure.observe(this, onLoginFailureObserver)
        viewModel.onNetworkError.observe(this, onNetworkErrorObserver)
    }

    private fun onLoginClicked() {
        if (viewModel.isValidForm(getEmail(), getPassword())) {
            showProgress()
            viewModel.login(this, getEmail(), getPassword())
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

    private fun onLoginSuccess() {
        hideProgress()
        startActivity<MainActivity>()
        finishAffinity()
    }

    private fun onLoginFailure() {
        hideProgress()
        showAlert(R.string.error_invalid_credentials)
    }

    private fun onNetworkError() {
        hideProgress()
        showAlert(R.string.error_network)
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
