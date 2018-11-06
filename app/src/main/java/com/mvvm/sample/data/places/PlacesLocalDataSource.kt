package com.mvvm.sample.data.places

import android.content.Context
import com.mvvm.sample.data.Place
import com.mvvm.sample.data.SampleDataBase
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class PlacesLocalDataSource: IPlacesDataSource {

    override fun savePlaces(context: Context, places: List<Place>) {
        doAsync {
            SampleDataBase.getInstance(context).placeDao().savePlaces(places)
        }
    }

    override fun getPlaces(context: Context, listener: PlacesRepository.IPlacesListener) {
        doAsync {
            val places = SampleDataBase.getInstance(context).placeDao().getPlaces()
            uiThread { listener.onPlacesSuccess(places) }
        }


    }

}