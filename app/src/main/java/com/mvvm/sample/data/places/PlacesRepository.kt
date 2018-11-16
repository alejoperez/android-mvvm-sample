package com.mvvm.sample.data.places

import android.arch.lifecycle.LiveData
import android.content.Context
import com.mvvm.sample.data.room.Place
import com.mvvm.sample.livedata.Event
import com.mvvm.sample.livedata.NetworkRequest

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

    override fun getPlaces(context: Context): LiveData<Event<List<Place>>> {
        return if (hasCache) {
            localDataSource.getPlaces(context)
        } else {
            object : NetworkRequest<Event<List<Place>>>() {
                override fun processBeforeDispatch(response: Event<List<Place>>) {
                    response.peekData()?.let {
                        savePlaces(context, it)
                        hasCache = true
                    }
                }

                override fun networkRequestToObserve(): LiveData<Event<List<Place>>> = remoteDataSource.getPlaces(context)

            }.performRequest()
        }
    }

    override fun savePlaces(context: Context,places: List<Place>) {
        localDataSource.savePlaces(context, places)
    }

    fun invalidateCache() {
        hasCache = false
    }
}