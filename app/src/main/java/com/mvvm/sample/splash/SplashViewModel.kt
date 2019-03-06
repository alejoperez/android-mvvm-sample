package com.mvvm.sample.splash

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.mvvm.sample.base.BaseViewModel
import com.mvvm.sample.data.user.UserRepository
import com.mvvm.sample.livedata.Event

class SplashViewModel(application: Application): BaseViewModel(application) {

    val isUserLoggedEvent = MutableLiveData<Event<Boolean>>()

    fun isUserLoggedIn() {
        isUserLoggedEvent.value = Event.success(UserRepository.getInstance().isLoggedIn(getApplication()))
    }

}