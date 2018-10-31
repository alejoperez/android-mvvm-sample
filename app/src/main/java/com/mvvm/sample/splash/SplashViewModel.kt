package com.mvvm.sample.splash

import android.content.Context
import com.mvvm.sample.base.BaseViewModel
import com.mvvm.sample.data.user.UserRepository
import com.mvvm.sample.livedata.SingleLiveEvent

class SplashViewModel: BaseViewModel() {

    val isUserLoggedEvent = SingleLiveEvent<Boolean>()

    fun isUserLoggedIn(context: Context) {
        isUserLoggedEvent.value = UserRepository.getInstance().isLoggedIn(context)
    }

}