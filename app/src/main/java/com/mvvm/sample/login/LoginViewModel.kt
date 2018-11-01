package com.mvvm.sample.login

import android.arch.lifecycle.MutableLiveData
import android.content.Context
import com.mvvm.sample.base.BaseViewModel
import com.mvvm.sample.data.user.UserRepository
import com.mvvm.sample.livedata.Event
import com.mvvm.sample.webservice.LoginRequest
import com.mvvm.sample.webservice.LoginResponse

class LoginViewModel: BaseViewModel(), UserRepository.ILoginListener {

    val onLoginSuccess = MutableLiveData<Event<Unit>>()
    val onLoginFailure = MutableLiveData<Event<Unit>>()

    fun isValidEmail(email: String): Boolean = email.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

    fun isValidPassword(password: String): Boolean = password.isNotEmpty()

    fun isValidForm(email: String, password: String): Boolean = isValidEmail(email) && isValidPassword(password)

    fun login(context: Context, email: String, password: String) = UserRepository.getInstance().login(context, LoginRequest(email, password),this)

    override fun onLoginSuccess(response: LoginResponse?) {
        onLoginSuccess.value = Event(Unit)
    }

    override fun onLoginFailure() {
        onLoginFailure.value = Event(Unit)
    }

    override fun onNetworkError() {
        onNetworkError.value = Event(Unit)
    }


}