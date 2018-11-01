package com.mvvm.sample.base

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.mvvm.sample.livedata.Event

abstract class BaseViewModel: ViewModel() {

    val onNetworkError = MutableLiveData<Event<Unit>>()
}