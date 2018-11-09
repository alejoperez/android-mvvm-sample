package com.mvvm.sample.data.places

import android.arch.lifecycle.LiveData
import android.content.Context
import com.mvvm.sample.data.room.Place
import com.mvvm.sample.livedata.DataResource

interface IPlacesDataSource {

    fun getPlaces(context: Context): LiveData<DataResource<List<Place>>>

    fun savePlaces(context: Context, places: List<Place>)
}