package com.mvvm.sample.main

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.mvvm.sample.base.BaseViewModel
import com.mvvm.sample.data.User
import com.mvvm.sample.data.user.UserRepository
import com.mvvm.sample.livedata.Event

class MainViewModel(application: Application) : BaseViewModel(application), UserRepository.IUserListener {

    val userEvent = MutableLiveData<Event<User?>>()
    val onLogoutSuccess = MutableLiveData<Event<Unit>>()

    fun getUser() {
        UserRepository.getInstance().getUser(getApplication(), this)
    }

    override fun onUserSuccess(user: User?) {
        userEvent.value = Event(user)
    }

    override fun onLoginFailure() {
        userEvent.value = Event(null)
    }

    override fun onNetworkError() {
        userEvent.value = Event(null)
    }

    fun logout() {
        UserRepository.getInstance().logout(getApplication())
        onLogoutSuccess.value = Event(Unit)
    }

}