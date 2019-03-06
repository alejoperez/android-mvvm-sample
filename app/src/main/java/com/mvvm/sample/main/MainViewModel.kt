package com.mvvm.sample.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.mvvm.sample.base.BaseViewModel
import com.mvvm.sample.data.room.User
import com.mvvm.sample.data.user.UserRepository
import com.mvvm.sample.livedata.Event

class MainViewModel(application: Application) : BaseViewModel(application) {

    private val getUserEvent = MutableLiveData<Event<Unit>>()

    val user: LiveData<Event<User>> = Transformations.switchMap(getUserEvent) {
        UserRepository.getInstance().getUser(getApplication())
    }

    val onLogoutSuccess = MutableLiveData<Event<Unit>>()

    fun getUser() {
        getUserEvent.value = Event.loading()
    }

    fun logout() {
        onLogoutSuccess.value = Event.success(UserRepository.getInstance().logout(getApplication()))
    }

}