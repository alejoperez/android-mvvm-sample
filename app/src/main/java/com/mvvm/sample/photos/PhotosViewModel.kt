package com.mvvm.sample.photos

import android.content.Context
import com.mvvm.sample.base.BaseViewModel
import com.mvvm.sample.data.Photo
import com.mvvm.sample.data.photos.PhotosRepository

class PhotosViewModel: BaseViewModel(), PhotosRepository.IPhotosListener {

    val onPhotosSuccess = SingleLiveEvent<List<Photo>>()
    val onPhotosFailure = SingleLiveEvent<Unit>()

    fun getPhotos(context: Context) {
        PhotosRepository.getInstance().getPhotos(context, this)
    }

    override fun onPhotosSuccess(photos: List<Photo>?) {
        onPhotosSuccess.value = photos
    }

    override fun onPhotosFailure() = onPhotosFailure.call()

    override fun onNetworkError() = onNetworkError.call()

}