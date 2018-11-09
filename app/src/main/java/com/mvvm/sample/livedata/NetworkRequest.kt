package com.mvvm.sample.livedata

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData

abstract class NetworkRequest<NetworkResponse> {

    abstract fun processBeforeDispatch(response: NetworkResponse)

    abstract fun networkRequestToObserve() : LiveData<NetworkResponse>

    fun performRequest(): LiveData<NetworkResponse> {
        val result = MediatorLiveData<NetworkResponse>()
        val liveData = networkRequestToObserve()
        result.addSource(liveData) {
            result.removeSource(liveData)
            if(it != null) {
                processBeforeDispatch(it)
            }
            result.value = it
        }
        return result
    }
}