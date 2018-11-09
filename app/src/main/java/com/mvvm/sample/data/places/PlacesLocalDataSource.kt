package com.mvvm.sample.data.places

import android.arch.lifecycle.LiveData
import android.content.Context
import com.mvvm.sample.data.room.Place
import com.mvvm.sample.data.room.SampleDataBase
import com.mvvm.sample.livedata.DataRequest
import com.mvvm.sample.livedata.DataResource
import org.jetbrains.anko.doAsync

class PlacesLocalDataSource: IPlacesDataSource {

    override fun savePlaces(context: Context, places: List<Place>) {
        doAsync {
            SampleDataBase.getInstance(context).placeDao().savePlaces(places)
        }
    }

    override fun getPlaces(context: Context): LiveData<DataResource<List<Place>>> = object : DataRequest<List<Place>>() {
        override fun dataRequestToObserve(): LiveData<List<Place>> = SampleDataBase.getInstance(context).placeDao().getPlaces()

    }.performRequest()

}