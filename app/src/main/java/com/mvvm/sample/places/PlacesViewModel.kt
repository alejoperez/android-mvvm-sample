package com.mvvm.sample.places

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.mvvm.sample.base.BaseViewModel
import com.mvvm.sample.data.room.Place
import com.mvvm.sample.data.places.PlacesRepository
import com.mvvm.sample.livedata.Event

class PlacesViewModel(application: Application): BaseViewModel(application) {

    private val placesEvent = MutableLiveData<Event<Unit>>()
    val places: LiveData<Event<List<Place>>> = Transformations.switchMap(placesEvent) {
        PlacesRepository.getInstance().getPlaces(getApplication())
    }

    fun getPlaces() {
        placesEvent.value = Event.loading()
    }

}