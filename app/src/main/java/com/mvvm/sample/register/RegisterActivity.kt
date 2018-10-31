package com.mvvm.sample.register

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import com.mvvm.sample.R
import com.mvvm.sample.base.BaseActivity
import com.mvvm.sample.extensions.getWhiteSpaceFilters
import com.mvvm.sample.login.LoginActivity
import com.mvvm.sample.main.MainActivity
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.startActivity

class RegisterActivity : BaseActivity() {

    private val viewModel by lazy { ViewModelProviders.of(this).get(RegisterViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        initView()
        initViewModel()
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

    private fun initViewModel() {
        viewModel.onRegisterSuccess.observe(this, onRegisterSuccessObserver)
        viewModel.onRegisterFailure.observe(this, onRegisterFailureObserver)
        viewModel.onNetworkError.observe(this, onNetworkErrorObserver)
    }

    private fun onRegisterClicked() {
        if (viewModel.isValidForm(getName(), getEmail(), getPassword())) {
            showProgress()
            viewModel.register(this, getName(), getEmail(), getPassword())
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

    private val onRegisterSuccessObserver = Observer<Unit> {
        hideProgress()
        startActivity<MainActivity>()
        finishAffinity()
    }

    private val onRegisterFailureObserver = Observer<Unit> {
        hideProgress()
        showAlert(R.string.error_user_already_exists)
    }

    private val onNetworkErrorObserver = Observer<Unit> {
        hideProgress()
        showAlert(R.string.error_network)
    }

    private fun getName(): String = etName.text.toString()

    private fun getEmail(): String = etEmail.text.toString()

    private fun getPassword(): String = etPassword.text.toString()

    private fun showProgress() {
        btRegister.visibility = View.INVISIBLE
        progress.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        btRegister.visibility = View.VISIBLE
        progress.visibility = View.INVISIBLE
    }

}
