package com.mvvm.sample.data.places

import android.content.Context
import com.mvvm.sample.data.Place
import io.realm.Realm

class PlacesLocalDataSource: IPlacesDataSource {

    override fun savePlaces(places: List<Place>) {
        Realm.getDefaultInstance().executeTransactionAsync {
            realm -> realm.insertOrUpdate(places)
        }
    }

    override fun getPlaces(context: Context, listener: PlacesRepository.IPlacesListener) {
        listener.onPlacesSuccess(Realm.getDefaultInstance().where(Place::class.java).findAll())
    }

}