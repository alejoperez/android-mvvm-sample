package com.mvvm.sample.data.photos

import android.content.Context
import com.mvvm.sample.data.Photo

interface IPhotosDataSource {

    fun getPhotos(context: Context, listener: PhotosRepository.IPhotosListener)

    fun savePhotos(photos: List<Photo>)

}