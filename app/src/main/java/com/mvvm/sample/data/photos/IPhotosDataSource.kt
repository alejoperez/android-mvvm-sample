package com.mvvm.sample.data.photos

import android.arch.lifecycle.LiveData
import android.content.Context
import com.mvvm.sample.data.room.Photo
import com.mvvm.sample.livedata.DataResource

interface IPhotosDataSource {

    fun getPhotos(context: Context): LiveData<DataResource<List<Photo>>>

    fun savePhotos(context: Context, photos: List<Photo>)

}