package com.mvvm.sample.register

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import com.mvvm.sample.base.BaseViewModel
import com.mvvm.sample.data.user.UserRepository
import com.mvvm.sample.livedata.DataResource
import com.mvvm.sample.livedata.Event
import com.mvvm.sample.webservice.RegisterRequest
import com.mvvm.sample.webservice.RegisterResponse

class RegisterViewModel(application: Application): BaseViewModel(application) {

    private val registerEvent = MutableLiveData<Event<List<String>>>()

    val registerResponse: LiveData<DataResource<RegisterResponse>> = Transformations.switchMap(registerEvent) { it ->
        val list = it?.getContentIfNotHandled()
        list?.let {
            val name = list[0]
            val email = list[1]
            val password = list[2]
            UserRepository.getInstance().register(getApplication(), RegisterRequest(name, email, password))
        }
    }

    fun isValidName(name: String): Boolean = name.isNotEmpty()

    fun isValidEmail(email: String): Boolean = email.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

    fun isValidPassword(password: String): Boolean = password.isNotEmpty()

    fun isValidForm(name: String, email: String, password: String): Boolean = isValidName(name) && isValidEmail(email) && isValidPassword(password)

    fun register(name: String, email: String, password: String) {
        registerEvent.value = Event(arrayListOf(name,email,password))
    }
}