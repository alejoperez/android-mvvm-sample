package com.mvvm.sample.photos

import com.mvvm.sample.base.IBaseView
import com.mvvm.sample.data.Photo

interface IPhotosContract {

    interface View: IBaseView {
        fun onPhotosSuccess(photos: List<Photo>?)
        fun onPhotosFailure()
        fun showProgress()
        fun hideProgress()
    }

    interface Presenter {
        fun getPhotos()
    }
}