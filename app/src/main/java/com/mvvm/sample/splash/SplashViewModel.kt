package com.mvvm.sample.splash

import android.arch.lifecycle.ViewModel
import android.content.Context
import com.mvvm.sample.data.user.UserRepository
import com.mvvm.sample.livedata.SingleLiveEvent

class SplashViewModel: ViewModel() {

    val isUserLoggedEvent = SingleLiveEvent<Boolean>()

    fun isUserLoggedIn(context: Context) {
        isUserLoggedEvent.value = UserRepository.getInstance().isLoggedIn(context)
    }

}