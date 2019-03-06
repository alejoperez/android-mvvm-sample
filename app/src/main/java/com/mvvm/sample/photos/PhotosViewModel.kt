package com.mvvm.sample.photos

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.databinding.ObservableBoolean
import com.mvvm.sample.base.BaseViewModel
import com.mvvm.sample.data.room.Photo
import com.mvvm.sample.data.photos.PhotosRepository
import com.mvvm.sample.livedata.Event

class PhotosViewModel(application: Application): BaseViewModel(application) {

    val isLoading = ObservableBoolean(false)

    private val getPhotos = MutableLiveData<Event<Unit>>()

    val photos: LiveData<Event<List<Photo>>> = Transformations.switchMap(getPhotos) {
        PhotosRepository.getInstance().getPhotos(getApplication())
    }

    fun getPhotos() {
        showProgress()
        getPhotos.value = Event.loading()
    }

    private fun showProgress() = isLoading.set(true)

    fun hideProgress() = isLoading.set(false)

}