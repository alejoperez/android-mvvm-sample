package com.mvvm.sample.data.places

import android.content.Context
import com.mvvm.sample.data.IBaseSourceListener
import com.mvvm.sample.data.Place

class PlacesRepository private constructor(
        private val localDataSource: IPlacesDataSource = PlacesLocalDataSource(),
        private val remoteDataSource: IPlacesDataSource = PlacesRemoteDataSource()) : IPlacesDataSource {


    private var hasCache = false

    companion object {
        @Volatile
        private var INSTANCE: PlacesRepository? = null

        fun getInstance(): PlacesRepository = INSTANCE ?: synchronized(this) {
            INSTANCE ?: PlacesRepository().also { INSTANCE = it }
        }
    }

    override fun getPlaces(context: Context, listener: IPlacesListener) {
        if (hasCache) {
            localDataSource.getPlaces(context, listener)
        } else {
            remoteDataSource.getPlaces(context, object : IPlacesListener{

                override fun onPlacesSuccess(places: List<Place>?) {
                    if (places != null) {
                        savePlaces(places)
                        listener.onPlacesSuccess(places)
                    } else {
                        listener.onPlacesFailure()
                    }
                }

                override fun onPlacesFailure() = listener.onPlacesFailure()

                override fun onNetworkError() = listener.onNetworkError()
            })
        }
    }

    override fun savePlaces(places: List<Place>) {
        localDataSource.savePlaces(places)
    }

    fun invalidateCache() {
        hasCache = false
    }


    interface IPlacesListener : IBaseSourceListener {
        fun onPlacesSuccess(places: List<Place>?)
        fun onPlacesFailure()
    }
}