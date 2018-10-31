package com.mvvm.sample.places

import android.content.Context
import com.mvvm.sample.base.BaseViewModel
import com.mvvm.sample.data.Place
import com.mvvm.sample.data.places.PlacesRepository

class PlacesViewModel: BaseViewModel(), PlacesRepository.IPlacesListener {

    val onPlacesSuccess = SingleLiveEvent<List<Place>>()
    val onPlacesFailure = SingleLiveEvent<Unit>()

    fun getPlaces(context: Context) = PlacesRepository.getInstance().getPlaces(context, this)

    override fun onPlacesSuccess(places: List<Place>?) {
        onPlacesSuccess.value = places
    }

    override fun onPlacesFailure() = onPlacesFailure.call()

    override fun onNetworkError() = onNetworkError.call()


}