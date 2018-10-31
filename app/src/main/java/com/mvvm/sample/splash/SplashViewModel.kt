package com.mvvm.sample.splash

import android.arch.lifecycle.MutableLiveData
import android.content.Context
import com.mvvm.sample.base.BaseViewModel
import com.mvvm.sample.data.user.UserRepository
import com.mvvm.sample.livedata.Event

class SplashViewModel: BaseViewModel() {

    val isUserLoggedEvent = MutableLiveData<Event<Boolean>>()

    fun isUserLoggedIn(context: Context) {
        isUserLoggedEvent.value = Event(UserRepository.getInstance().isLoggedIn(context))
    }

}