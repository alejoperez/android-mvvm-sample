package com.mvvm.sample.places

import android.arch.lifecycle.MutableLiveData
import android.content.Context
import com.mvvm.sample.base.BaseViewModel
import com.mvvm.sample.data.Place
import com.mvvm.sample.data.places.PlacesRepository
import com.mvvm.sample.livedata.Event

class PlacesViewModel: BaseViewModel(), PlacesRepository.IPlacesListener {

    val onPlacesSuccess = MutableLiveData<Event<List<Place>?>>()
    val onPlacesFailure = MutableLiveData<Event<Unit>>()

    fun getPlaces(context: Context) = PlacesRepository.getInstance().getPlaces(context, this)

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