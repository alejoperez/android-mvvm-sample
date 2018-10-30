package com.mvvm.sample.photos

import com.mvvm.sample.R
import com.mvvm.sample.data.Photo
import com.mvvm.sample.data.photos.PhotosRepository

class PhotosPresenter(private val view: IPhotosContract.View): IPhotosContract.Presenter, PhotosRepository.IPhotosListener {

    override fun getPhotos() {
        view.showProgress()
        PhotosRepository.getInstance().getPhotos(view.getViewContext(), this)
    }

    override fun onPhotosSuccess(photos: List<Photo>?) {
        if (view.isActive()) {
            view.hideProgress()
            view.onPhotosSuccess(photos)
        }
    }

    override fun onPhotosFailure() {
        if (view.isActive()) {
            view.hideProgress()
            view.onPhotosFailure()
        }
    }

    override fun onNetworkError() {
        if (view.isActive()) {
            view.hideProgress()
            view.showAlert(R.string.error_network)
        }
    }
}