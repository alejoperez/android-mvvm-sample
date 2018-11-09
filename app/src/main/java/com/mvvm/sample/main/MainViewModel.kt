package com.mvvm.sample.main

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import com.mvvm.sample.base.BaseViewModel
import com.mvvm.sample.data.room.User
import com.mvvm.sample.data.user.UserRepository
import com.mvvm.sample.livedata.DataResource
import com.mvvm.sample.livedata.Event

class MainViewModel(application: Application) : BaseViewModel(application) {

    private val getUserEvent = MutableLiveData<Event<Unit>>()
    val user: LiveData<DataResource<User>> = Transformations.switchMap(getUserEvent) {
        UserRepository.getInstance().getUser(getApplication())
    }

    val onLogoutSuccess = MutableLiveData<Event<Unit>>()

    fun getUser() {
        getUserEvent.value = Event(Unit)
    }

    fun logout() {
        UserRepository.getInstance().logout(getApplication())
        onLogoutSuccess.value = Event(Unit)
    }

}