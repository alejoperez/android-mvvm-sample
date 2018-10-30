package com.mvvm.sample.data.places

import android.content.Context
import com.mvvm.sample.data.Place

interface IPlacesDataSource {

    fun getPlaces(context: Context, listener: PlacesRepository.IPlacesListener)

    fun savePlaces(places: List<Place>)
}