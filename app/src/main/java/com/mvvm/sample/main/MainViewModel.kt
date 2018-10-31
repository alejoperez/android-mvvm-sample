package com.mvvm.sample.main

import android.arch.lifecycle.ViewModel
import android.content.Context
import com.mvvm.sample.data.User
import com.mvvm.sample.data.user.UserRepository
import com.mvvm.sample.livedata.SingleLiveEvent

class MainViewModel : ViewModel() {

    val user = SingleLiveEvent<User>()
    val onLogoutSuccess = SingleLiveEvent<Unit>()

    fun getUser() {
        user.value = UserRepository.getInstance().getUser()
    }

    fun logout(context: Context) {
        UserRepository.getInstance().logout(context)
        onLogoutSuccess.call()
    }

}