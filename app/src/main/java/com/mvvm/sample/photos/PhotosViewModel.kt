package com.mvvm.sample.photos

import android.arch.lifecycle.ViewModel
import android.content.Context
import com.mvvm.sample.data.Photo
import com.mvvm.sample.data.photos.PhotosRepository
import com.mvvm.sample.livedata.SingleLiveEvent

class PhotosViewModel: ViewModel(), PhotosRepository.IPhotosListener {

    val onPhotosSuccess = SingleLiveEvent<List<Photo>>()
    val onPhotosFailure = SingleLiveEvent<Unit>()
    val onNetworkError = SingleLiveEvent<Unit>()

    fun getPhotos(context: Context) {
        PhotosRepository.getInstance().getPhotos(context, this)
    }

    override fun onPhotosSuccess(photos: List<Photo>?) {
        onPhotosSuccess.value = photos
    }

    override fun onPhotosFailure() = onPhotosFailure.call()

    override fun onNetworkError() = onNetworkError.call()

}