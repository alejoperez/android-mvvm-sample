package com.mvvm.sample.register

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import com.mvvm.sample.R
import com.mvvm.sample.base.BaseViewModel
import com.mvvm.sample.data.user.UserRepository
import com.mvvm.sample.databinding.BindingAdapters
import com.mvvm.sample.utils.checkField
import com.mvvm.sample.utils.getValueOrDefault
import com.mvvm.sample.livedata.Event
import com.mvvm.sample.webservice.RegisterRequest
import com.mvvm.sample.webservice.RegisterResponse

class RegisterViewModel(application: Application): BaseViewModel(application) {

    val name = ObservableField("")
    val email = ObservableField("")
    val password = ObservableField("")

    val errorName = ObservableInt(BindingAdapters.EMPTY)
    val errorEmail = ObservableInt(BindingAdapters.EMPTY)
    val errorPassword = ObservableInt(BindingAdapters.EMPTY)

    val isLoading = ObservableField(false)

    private val registerEvent = MutableLiveData<Event<Unit>>()

    val registerResponse: LiveData<Event<RegisterResponse>> = Transformations.switchMap(registerEvent) {
        UserRepository.getInstance().register(getApplication(), RegisterRequest(name.getValueOrDefault(), email.getValueOrDefault(), password.getValueOrDefault()))
    }

    fun register() {
        if (isValidForm()) {
            showProgress()
            registerEvent.value = Event.loading()
        } else {
            errorName.checkField(R.string.error_name_empty,isValidName())
            errorEmail.checkField(R.string.error_invalid_email,isValidEmail())
            errorPassword.checkField(R.string.error_empty_password,isValidPassword())
        }
    }

    private fun isValidName(): Boolean = name.getValueOrDefault().isNotEmpty()

    private fun isValidEmail(): Boolean = email.getValueOrDefault().isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email.getValueOrDefault()).matches()

    private fun isValidPassword(): Boolean = password.getValueOrDefault().isNotEmpty()

    private fun isValidForm(): Boolean = isValidName() && isValidEmail() && isValidPassword()

    private fun showProgress() = isLoading.set(true)

    fun hideProgress() = isLoading.set(false)
}