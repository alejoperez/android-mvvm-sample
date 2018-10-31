package com.mvvm.sample.register

import android.os.Bundle
import android.view.View
import com.mvvm.sample.R
import com.mvvm.sample.base.BaseActivity
import com.mvvm.sample.extensions.getWhiteSpaceFilters
import com.mvvm.sample.livedata.EventObserver
import com.mvvm.sample.login.LoginActivity
import com.mvvm.sample.main.MainActivity
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.startActivity

class RegisterActivity : BaseActivity() {

    private val viewModel by lazy { obtainViewModel(RegisterViewModel::class.java) }

    private val onRegisterSuccessObserver = EventObserver<Unit> { onRegisterSuccess() }

    private val onRegisterFailureObserver = EventObserver<Unit> { onRegisterFailure() }

    private val onNetworkErrorObserver = EventObserver<Unit> { onNetworkError() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        initViewModel()
        initView()
    }

    private fun initViewModel() {
        viewModel.onRegisterSuccess.observe(this, onRegisterSuccessObserver)
        viewModel.onRegisterFailure.observe(this, onRegisterFailureObserver)
        viewModel.onNetworkError.observe(this, onNetworkErrorObserver)
    }

    private fun initView() {
        etEmail.filters = getWhiteSpaceFilters()
        etPassword.filters = getWhiteSpaceFilters()

        btRegister.setOnClickListener {
            onRegisterClicked()
        }

        tvGoToLogin.setOnClickListener {
            startActivity<LoginActivity>()
        }
    }

    private fun onRegisterSuccess() {
        hideProgress()
        startActivity<MainActivity>()
        finishAffinity()
    }

    private fun onRegisterFailure() {
        hideProgress()
        showAlert(R.string.error_user_already_exists)
    }

    private fun onNetworkError() {
        hideProgress()
        showAlert(R.string.error_network)
    }

    private fun getName(): String = etName.text.toString()

    private fun getEmail(): String = etEmail.text.toString()

    private fun getPassword(): String = etPassword.text.toString()


    private fun onRegisterClicked() {
        if (viewModel.isValidForm(getName(), getEmail(), getPassword())) {
            showProgress()
            viewModel.register(getViewContext(), getName(), getEmail(), getPassword())
        } else {
            if (!viewModel.isValidName(getName())) {
                etName.error = getString(R.string.error_name_empty)
            }
            if (!viewModel.isValidEmail(getEmail())) {
                etEmail.error = getString(R.string.error_invalid_email)
            }
            if (!viewModel.isValidPassword(getPassword())) {
                etPassword.error = getString(R.string.error_empty_password)
            }
        }
    }

    private fun showProgress() {
        btRegister.visibility = View.INVISIBLE
        progress.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        btRegister.visibility = View.VISIBLE
        progress.visibility = View.INVISIBLE
    }

}
