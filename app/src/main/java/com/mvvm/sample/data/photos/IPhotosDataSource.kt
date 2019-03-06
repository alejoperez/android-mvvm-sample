package com.mvvm.sample.data.photos

import androidx.lifecycle.LiveData
import android.content.Context
import com.mvvm.sample.data.room.Photo
import com.mvvm.sample.livedata.Event

interface IPhotosDataSource {

    fun getPhotos(context: Context): LiveData<Event<List<Photo>>>

    fun savePhotos(context: Context, photos: List<Photo>)

}