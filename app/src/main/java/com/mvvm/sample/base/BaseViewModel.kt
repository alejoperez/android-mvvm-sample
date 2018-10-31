package com.mvvm.sample.base

import android.arch.lifecycle.ViewModel
import com.mvvm.sample.livedata.SingleLiveEvent

abstract class BaseViewModel: ViewModel() {

    val onNetworkError = SingleLiveEvent<Unit>()
}