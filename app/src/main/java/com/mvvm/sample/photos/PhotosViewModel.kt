package com.mvvm.sample.photos

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.mvvm.sample.base.BaseViewModel
import com.mvvm.sample.data.Photo
import com.mvvm.sample.data.photos.PhotosRepository
import com.mvvm.sample.livedata.Event

class PhotosViewModel(application: Application): BaseViewModel(application), PhotosRepository.IPhotosListener {

    val onPhotosSuccess = MutableLiveData<Event<List<Photo>?>>()
    val onPhotosFailure = MutableLiveData<Event<Unit>>()

    fun getPhotos() {
        PhotosRepository.getInstance().getPhotos(getApplication(), this)
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