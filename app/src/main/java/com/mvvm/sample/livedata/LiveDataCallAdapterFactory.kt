package com.mvvm.sample.livedata

import android.arch.lifecycle.LiveData

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.util.concurrent.atomic.AtomicBoolean

class LiveDataCallAdapterFactory private constructor() : CallAdapter.Factory() {

    companion object {
        fun create(): LiveDataCallAdapterFactory {
            return LiveDataCallAdapterFactory()
        }
    }

    override fun get(returnType: Type, annotations: Array<kotlin.Annotation>, retrofit: Retrofit): CallAdapter<*, *>? {
        if (CallAdapter.Factory.getRawType(returnType) != LiveData::class.java) {
            return null
        }
        val observableType = CallAdapter.Factory.getParameterUpperBound(0, returnType as ParameterizedType)
        val rawObservableType = CallAdapter.Factory.getRawType(observableType)
        if (rawObservableType != DataResource::class.java) {
            throw IllegalArgumentException("type must be a DataResource")
        }
        if (observableType !is ParameterizedType) {
            throw IllegalArgumentException("resource must be ParameterizedType")
        }
        val bodyType = CallAdapter.Factory.getParameterUpperBound(0, observableType)
        return LiveDataCallAdapter<Any>(bodyType)
    }

    class LiveDataCallAdapter<R>(private val responseType: Type) :
            CallAdapter<R, LiveData<DataResource<R>>> {

        override fun responseType() = responseType

        override fun adapt(call: Call<R>): LiveData<DataResource<R>> {
            return object : LiveData<DataResource<R>>() {
                private var started = AtomicBoolean(false)
                override fun onActive() {
                    super.onActive()
                    if (started.compareAndSet(false, true)) {
                        call.enqueue(object : Callback<R> {
                            override fun onResponse(call: Call<R>, response: Response<R>) {
                                if (response.isSuccessful) {
                                    postValue(DataResource.success(response.body()))
                                } else {
                                    postValue(DataResource.failure())
                                }
                            }

                            override fun onFailure(call: Call<R>, throwable: Throwable) {
                                postValue(DataResource.networkError())
                            }
                        })
                    }
                }
            }
        }
    }
}