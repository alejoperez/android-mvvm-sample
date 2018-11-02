package com.mvvm.sample.data.places

import android.content.Context
import com.mvvm.sample.data.Place
import com.mvvm.sample.data.SampleDataBase

class PlacesLocalDataSource: IPlacesDataSource {

    override fun savePlaces(context: Context, places: List<Place>) = SampleDataBase.getInstance(context).placeDao().savePlaces(places)

    override fun getPlaces(context: Context, listener: PlacesRepository.IPlacesListener) {
        listener.onPlacesSuccess(SampleDataBase.getInstance(context).placeDao().getPlaces())
    }

}