package com.mvvm.sample.main

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.mvvm.sample.base.BaseViewModel
import com.mvvm.sample.data.User
import com.mvvm.sample.data.user.UserRepository
import com.mvvm.sample.livedata.Event

class MainViewModel(application: Application) : BaseViewModel(application) {

    val user = MutableLiveData<Event<User?>>()
    val onLogoutSuccess = MutableLiveData<Event<Unit>>()

    fun getUser() {
        user.value = Event(UserRepository.getInstance().getUser())
    }

    fun logout() {
        UserRepository.getInstance().logout(getApplication())
        onLogoutSuccess.value = Event(Unit)
    }

}