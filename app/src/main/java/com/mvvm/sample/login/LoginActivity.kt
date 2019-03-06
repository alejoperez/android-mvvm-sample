package com.mvvm.sample.login

import androidx.lifecycle.Observer
import com.mvvm.sample.BR
import com.mvvm.sample.R
import com.mvvm.sample.base.BaseActivity
import com.mvvm.sample.databinding.ActivityLoginBinding
import com.mvvm.sample.livedata.Event
import com.mvvm.sample.livedata.Status
import com.mvvm.sample.main.MainActivity
import com.mvvm.sample.utils.EditTextUtils
import com.mvvm.sample.webservice.LoginResponse
import org.jetbrains.anko.startActivity

class LoginActivity : BaseActivity<LoginViewModel,ActivityLoginBinding>() {

    override fun getLayoutId(): Int = R.layout.activity_login
    override fun getViewModelClass(): Class<LoginViewModel> = LoginViewModel::class.java
    override fun getVariablesToBind(): Map<Int, Any> = mapOf(
            BR.viewModel to viewModel,
            BR.etUtils to EditTextUtils
    )

    override fun initViewModel() {
        super.initViewModel()
        viewModel.loginResponse.observe(this, onLoginResponseObserver)
    }

    private val onLoginResponseObserver = Observer<Event<LoginResponse>> {
        viewModel.hideProgress()
        if (it != null) {
            onLoginResponse(it)
        } else {
            onLoginFailure()
        }
    }

    private fun onLoginResponse(response: Event<LoginResponse>) {
        when (response.status) {
            Status.SUCCESS -> onLoginSuccess()
            Status.FAILURE -> onLoginFailure()
            Status.NETWORK_ERROR -> onNetworkError()
            else -> Unit
        }
    }

    private fun onLoginSuccess() {
        startActivity<MainActivity>()
        finishAffinity()
    }

    private fun onLoginFailure() = showAlert(R.string.error_invalid_credentials)
}
