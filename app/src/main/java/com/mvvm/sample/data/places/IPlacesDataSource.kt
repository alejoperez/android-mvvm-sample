package com.mvvm.sample.data.places

import androidx.lifecycle.LiveData
import android.content.Context
import com.mvvm.sample.data.room.Place
import com.mvvm.sample.livedata.Event

interface IPlacesDataSource {

    fun getPlaces(context: Context): LiveData<Event<List<Place>>>

    fun savePlaces(context: Context, places: List<Place>)
}