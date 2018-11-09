package com.mvvm.sample.data.photos

import android.arch.lifecycle.LiveData
import android.content.Context
import com.mvvm.sample.data.room.Photo
import com.mvvm.sample.data.room.SampleDataBase
import com.mvvm.sample.livedata.DataRequest
import com.mvvm.sample.livedata.DataResource
import org.jetbrains.anko.doAsync

class PhotosLocalDataSource: IPhotosDataSource {

    override fun savePhotos(context: Context, photos: List<Photo>) {
        doAsync {
            SampleDataBase.getInstance(context).photoDao().savePhotos(photos)
        }
    }

    override fun getPhotos(context: Context): LiveData<DataResource<List<Photo>>> {
        return object : DataRequest<List<Photo>>() {
            override fun dataRequestToObserve(): LiveData<List<Photo>> = SampleDataBase.getInstance(context).photoDao().getPhotos()
        }.performRequest()
    }

}