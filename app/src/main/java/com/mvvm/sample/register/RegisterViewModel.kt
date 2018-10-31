package com.mvvm.sample.register

import android.arch.lifecycle.ViewModel
import android.content.Context
import com.mvvm.sample.data.user.UserRepository
import com.mvvm.sample.livedata.SingleLiveEvent
import com.mvvm.sample.webservice.RegisterRequest
import com.mvvm.sample.webservice.RegisterResponse

class RegisterViewModel: ViewModel(), UserRepository.IRegisterListener {

    val onRegisterSuccess = SingleLiveEvent<Unit>()
    val onRegisterFailure = SingleLiveEvent<Unit>()
    val onNetworkError = SingleLiveEvent<Unit>()

    fun isValidName(name: String): Boolean = name.isNotEmpty()

    fun isValidEmail(email: String): Boolean = email.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

    fun isValidPassword(password: String): Boolean = password.isNotEmpty()

    fun isValidForm(name: String, email: String, password: String): Boolean = isValidName(name) && isValidEmail(email) && isValidPassword(password)

    fun register(context: Context, name: String, email: String, password: String) {
        UserRepository.getInstance().register(context, RegisterRequest(name, email, password),this)
    }

    override fun onRegisterSuccess(response: RegisterResponse?) = onRegisterSuccess.call()

    override fun onRegisterFailure() = onRegisterFailure.call()

    override fun onNetworkError() = onNetworkError.call()

}