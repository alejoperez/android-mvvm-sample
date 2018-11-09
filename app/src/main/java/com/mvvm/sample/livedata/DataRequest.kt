package com.mvvm.sample.livedata

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData

abstract class DataRequest<Data> {

    abstract fun dataRequestToObserve() : LiveData<Data>

    fun performRequest(): LiveData<DataResource<Data>> {
        val result = MediatorLiveData<DataResource<Data>>()
        val liveData = dataRequestToObserve()
        result.addSource(liveData) {
            result.removeSource(liveData)
            result.value = DataResource.success(it)
        }
        return result
    }
}