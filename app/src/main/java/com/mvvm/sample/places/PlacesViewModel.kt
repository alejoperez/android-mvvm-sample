package com.mvvm.sample.places

import android.arch.lifecycle.ViewModel
import android.content.Context
import com.mvvm.sample.data.Place
import com.mvvm.sample.data.places.PlacesRepository
import com.mvvm.sample.livedata.SingleLiveEvent

class PlacesViewModel: ViewModel(), PlacesRepository.IPlacesListener {

    val onPlacesSuccess = SingleLiveEvent<List<Place>>()
    val onPlacesFailure = SingleLiveEvent<Unit>()
    val onNetworkError = SingleLiveEvent<Unit>()

    fun getPlaces(context: Context) = PlacesRepository.getInstance().getPlaces(context, this)

    override fun onPlacesSuccess(places: List<Place>?) {
        onPlacesSuccess.value = places
    }

    override fun onPlacesFailure() = onPlacesFailure.call()

    override fun onNetworkError() = onNetworkError.call()


}