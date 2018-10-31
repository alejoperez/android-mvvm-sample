package com.mvvm.sample.base

import android.arch.lifecycle.ViewModel

abstract class BaseViewModel: ViewModel() {

    val onNetworkError = SingleLiveEvent<Unit>()
}