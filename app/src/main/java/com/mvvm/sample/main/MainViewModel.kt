package com.mvvm.sample.main

import android.arch.lifecycle.MutableLiveData
import android.content.Context
import com.mvvm.sample.base.BaseViewModel
import com.mvvm.sample.data.User
import com.mvvm.sample.data.user.UserRepository
import com.mvvm.sample.livedata.Event

class MainViewModel : BaseViewModel() {

    val user = MutableLiveData<Event<User?>>()
    val onLogoutSuccess = MutableLiveData<Event<Unit>>()

    fun getUser() {
        user.value = Event(UserRepository.getInstance().getUser())
    }

    fun logout(context: Context) {
        UserRepository.getInstance().logout(context)
        onLogoutSuccess.value = Event(Unit)
    }

}