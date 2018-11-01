package com.mvvm.sample.base

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.mvvm.sample.livedata.Event

abstract class BaseViewModel(application: Application): AndroidViewModel(application) {

    val onNetworkError = MutableLiveData<Event<Unit>>()
}