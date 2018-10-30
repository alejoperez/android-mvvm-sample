package com.mvvm.sample.places

import com.mvvm.sample.R
import com.mvvm.sample.data.Place
import com.mvvm.sample.data.places.PlacesRepository

class PlacesPresenter(private val view: IPlacesContract.View): IPlacesContract.Presenter, PlacesRepository.IPlacesListener {

    override fun getPlaces() {
        PlacesRepository.getInstance().getPlaces(view.getViewContext(), this)
    }

    override fun onPlacesSuccess(places: List<Place>?) {
        if (view.isActive()) {
            view.onPlacesSuccess(places)
        }
    }

    override fun onPlacesFailure() {
        if (view.isActive()) {
            view.onPlacesFailure()
        }
    }

    override fun onNetworkError() {
        if (view.isActive()) {
            view.showAlert(R.string.error_network)
        }
    }
}