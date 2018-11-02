package com.mvvm.sample.data.photos

import android.content.Context
import com.mvvm.sample.data.Photo
import com.mvvm.sample.data.SampleDataBase

class PhotosLocalDataSource: IPhotosDataSource {

    override fun savePhotos(context: Context, photos: List<Photo>) = SampleDataBase.getInstance(context).photoDao().savePhotos(photos)

    override fun getPhotos(context: Context, listener: PhotosRepository.IPhotosListener) {
        listener.onPhotosSuccess(SampleDataBase.getInstance(context).photoDao().getPhotos())
    }

}