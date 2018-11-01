package com.mvvm.sample.photos

import android.arch.lifecycle.MutableLiveData
import android.content.Context
import com.mvvm.sample.base.BaseViewModel
import com.mvvm.sample.data.Photo
import com.mvvm.sample.data.photos.PhotosRepository
import com.mvvm.sample.livedata.Event

class PhotosViewModel: BaseViewModel(), PhotosRepository.IPhotosListener {

    val onPhotosSuccess = MutableLiveData<Event<List<Photo>?>>()
    val onPhotosFailure = MutableLiveData<Event<Unit>>()

    fun getPhotos(context: Context) {
        PhotosRepository.getInstance().getPhotos(context, this)
    }

    override fun onPhotosSuccess(photos: List<Photo>?) {
        onPhotosSuccess.value = Event(photos)
    }

    override fun onPhotosFailure() {
        onPhotosFailure.value = Event(Unit)
    }

    override fun onNetworkError() {
        onNetworkError.value = Event(Unit)
    }

}