package com.mvvm.sample.register

import android.arch.lifecycle.MutableLiveData
import android.content.Context
import com.mvvm.sample.base.BaseViewModel
import com.mvvm.sample.data.user.UserRepository
import com.mvvm.sample.livedata.Event
import com.mvvm.sample.webservice.RegisterRequest
import com.mvvm.sample.webservice.RegisterResponse

class RegisterViewModel: BaseViewModel(), UserRepository.IRegisterListener {

    val onRegisterSuccess = MutableLiveData<Event<Unit>>()
    val onRegisterFailure = MutableLiveData<Event<Unit>>()

    fun isValidName(name: String): Boolean = name.isNotEmpty()

    fun isValidEmail(email: String): Boolean = email.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

    fun isValidPassword(password: String): Boolean = password.isNotEmpty()

    fun isValidForm(name: String, email: String, password: String): Boolean = isValidName(name) && isValidEmail(email) && isValidPassword(password)

    fun register(context: Context, name: String, email: String, password: String) = UserRepository.getInstance().register(context, RegisterRequest(name, email, password),this)

    override fun onRegisterSuccess(response: RegisterResponse?) {
        onRegisterSuccess.value = Event(Unit)
    }

    override fun onRegisterFailure() {
        onRegisterFailure.value = Event(Unit)
    }

    override fun onNetworkError() {
        onNetworkError.value = Event(Unit)
    }

}