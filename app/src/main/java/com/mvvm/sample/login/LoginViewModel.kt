package com.mvvm.sample.login

import android.arch.lifecycle.ViewModel
import android.content.Context
import com.mvvm.sample.data.user.UserRepository
import com.mvvm.sample.livedata.SingleLiveEvent
import com.mvvm.sample.webservice.LoginRequest
import com.mvvm.sample.webservice.LoginResponse

class LoginViewModel: ViewModel(), UserRepository.ILoginListener {

    val onLoginSuccess = SingleLiveEvent<Unit>()
    val onLoginFailure = SingleLiveEvent<Unit>()
    val onNetworkError = SingleLiveEvent<Unit>()

    fun isValidEmail(email: String): Boolean = email.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

    fun isValidPassword(password: String): Boolean = password.isNotEmpty()

    fun isValidForm(email: String, password: String): Boolean = isValidEmail(email) && isValidPassword(password)

    fun login(context: Context, email: String, password: String) = UserRepository.getInstance().login(context, LoginRequest(email, password),this)

    override fun onLoginSuccess(response: LoginResponse?) = onLoginSuccess.call()

    override fun onLoginFailure() = onLoginFailure.call()

    override fun onNetworkError() = onNetworkError.call()


}