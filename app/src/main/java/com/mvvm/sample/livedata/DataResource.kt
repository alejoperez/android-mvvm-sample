package com.mvvm.sample.livedata

class DataResource<T> private constructor(val status: Status, val data: T?) {

    companion object {
        fun <T> success(data: T?): DataResource<T> {
            return DataResource(Status.SUCCESS, data)
        }

        fun <T> failure(): DataResource<T> {
            return DataResource(Status.FAILURE, null)
        }

        fun <T> networkError(): DataResource<T> {
            return DataResource(Status.NETWORK_ERROR, null)
        }
    }

}

enum class Status { SUCCESS, FAILURE, NETWORK_ERROR }