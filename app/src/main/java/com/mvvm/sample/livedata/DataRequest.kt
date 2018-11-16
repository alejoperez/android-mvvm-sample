package com.mvvm.sample.livedata

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData

abstract class DataRequest<Data> {

    abstract fun dataRequestToObserve() : LiveData<Data>

    fun performRequest(): LiveData<Event<Data>> {
        val result = MediatorLiveData<Event<Data>>()
        val liveData = dataRequestToObserve()
        result.addSource(liveData) {
            result.removeSource(liveData)
            if (it != null) {
                result.value = Event.success(it)
            }else {
                result.value = Event.failure()
            }

        }
        return result
    }
}