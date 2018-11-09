package com.mvvm.sample.photos

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import com.mvvm.sample.base.BaseViewModel
import com.mvvm.sample.data.room.Photo
import com.mvvm.sample.data.photos.PhotosRepository
import com.mvvm.sample.livedata.DataResource
import com.mvvm.sample.livedata.Event

class PhotosViewModel(application: Application): BaseViewModel(application) {

    private val getPhotos = MutableLiveData<Event<Unit>>()
    val photos: LiveData<DataResource<List<Photo>>> = Transformations.switchMap(getPhotos) {
        PhotosRepository.getInstance().getPhotos(getApplication())
    }

    fun getPhotos() {
        getPhotos.value = Event(Unit)
    }

}