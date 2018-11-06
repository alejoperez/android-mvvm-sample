package com.mvvm.sample.data.photos

import android.content.Context
import com.mvvm.sample.data.IBaseSourceListener
import com.mvvm.sample.data.Photo

class PhotosRepository private constructor(
        private val localDataSource: IPhotosDataSource = PhotosLocalDataSource(),
        private val remoteDataSource: IPhotosDataSource = PhotosRemoteDataSource()) : IPhotosDataSource {


    private var hasCache = false

    companion object {
        @Volatile
        private var INSTANCE: PhotosRepository? = null

        fun getInstance(): PhotosRepository = INSTANCE ?: synchronized(this) {
            INSTANCE ?: PhotosRepository().also { INSTANCE = it }
        }
    }

    override fun getPhotos(context: Context, listener: IPhotosListener) {
        if (hasCache) {
            localDataSource.getPhotos(context, listener)
        } else {
            remoteDataSource.getPhotos(context, object : IPhotosListener{

                override fun onPhotosSuccess(photos: List<Photo>?) {
                    if (photos != null) {
                        savePhotos(context, photos)
                        listener.onPhotosSuccess(photos)
                        hasCache = true
                    } else {
                        listener.onPhotosFailure()
                    }
                }

                override fun onPhotosFailure() = listener.onPhotosFailure()

                override fun onNetworkError() = listener.onNetworkError()
            })
        }
    }

    override fun savePhotos(context: Context,photos: List<Photo>) {
        localDataSource.savePhotos(context, photos)
    }

    fun invalidateCache() {
        hasCache = false
    }


    interface IPhotosListener : IBaseSourceListener {
        fun onPhotosSuccess(photos: List<Photo>?)
        fun onPhotosFailure()
    }
}