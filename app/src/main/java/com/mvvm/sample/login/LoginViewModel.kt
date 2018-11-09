package com.mvvm.sample.login

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import com.mvvm.sample.base.BaseViewModel
import com.mvvm.sample.data.user.UserRepository
import com.mvvm.sample.livedata.DataResource
import com.mvvm.sample.livedata.Event
import com.mvvm.sample.webservice.LoginRequest
import com.mvvm.sample.webservice.LoginResponse

class LoginViewModel(application: Application): BaseViewModel(application) {

    private val loginEvent = MutableLiveData<Event<Pair<String,String>>>()
    val loginResponse: LiveData<DataResource<LoginResponse>> = Transformations.switchMap(loginEvent) { it ->
        val pair = it.getContentIfNotHandled()
        pair?.let {
            UserRepository.getInstance().login(getApplication(), LoginRequest(it.first, it.second))
        }
    }

    fun isValidEmail(email: String): Boolean = email.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

    fun isValidPassword(password: String): Boolean = password.isNotEmpty()

    fun isValidForm(email: String, password: String): Boolean = isValidEmail(email) && isValidPassword(password)

    fun login(email: String, password: String) {
        loginEvent.value = Event(email to password)
    }
}