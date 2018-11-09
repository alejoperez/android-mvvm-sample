package com.mvvm.sample.data.photos

import android.arch.lifecycle.LiveData
import android.content.Context
import com.mvvm.sample.data.room.Photo
import com.mvvm.sample.livedata.DataResource
import com.mvvm.sample.livedata.NetworkRequest

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

    override fun getPhotos(context: Context): LiveData<DataResource<List<Photo>>> {
        return if (hasCache) {
            localDataSource.getPhotos(context)
        } else {
            object : NetworkRequest<DataResource<List<Photo>>>() {

                override fun processBeforeDispatch(response: DataResource<List<Photo>>) {
                    response.data?.let {
                        savePhotos(context, it)
                        hasCache = true
                    }
                }

                override fun networkRequestToObserve(): LiveData<DataResource<List<Photo>>> = remoteDataSource.getPhotos(context)

            }.performRequest()
        }
    }

    override fun savePhotos(context: Context,photos: List<Photo>) {
        localDataSource.savePhotos(context, photos)
    }

    fun invalidateCache() {
        hasCache = false
    }
}