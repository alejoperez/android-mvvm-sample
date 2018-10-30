package com.mvvm.sample.places

import com.mvvm.sample.base.IBaseView
import com.mvvm.sample.data.Place

interface IPlacesContract {

    interface View: IBaseView {
        fun onPlacesSuccess(places: List<Place>?)
        fun onPlacesFailure()
    }

    interface Presenter {
        fun getPlaces()
    }
}