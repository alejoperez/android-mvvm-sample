package com.mvvm.sample.places

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.mvvm.sample.base.BaseViewModel
import com.mvvm.sample.data.Place
import com.mvvm.sample.data.places.PlacesRepository
import com.mvvm.sample.livedata.Event

class PlacesViewModel(application: Application): BaseViewModel(application), PlacesRepository.IPlacesListener {

    val onPlacesSuccess = MutableLiveData<Event<List<Place>?>>()
    val onPlacesFailure = MutableLiveData<Event<Unit>>()

    fun getPlaces() = PlacesRepository.getInstance().getPlaces(getApplication(), this)

    override fun onPlacesSuccess(places: List<Place>?) {
        onPlacesSuccess.value = Event(places)
    }

    override fun onPlacesFailure() {
        onPlacesFailure.value = Event(Unit)
    }

    override fun onNetworkError() {
        onNetworkError.value = Event(Unit)
    }


}