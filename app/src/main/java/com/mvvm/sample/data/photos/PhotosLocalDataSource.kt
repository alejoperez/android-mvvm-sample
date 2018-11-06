package com.mvvm.sample.data.photos

import android.content.Context
import com.mvvm.sample.data.Photo
import com.mvvm.sample.data.SampleDataBase
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class PhotosLocalDataSource: IPhotosDataSource {

    override fun savePhotos(context: Context, photos: List<Photo>) {
        doAsync {
            SampleDataBase.getInstance(context).photoDao().savePhotos(photos)
        }
    }

    override fun getPhotos(context: Context, listener: PhotosRepository.IPhotosListener) {
        doAsync {
            val photos = SampleDataBase.getInstance(context).photoDao().getPhotos()
            uiThread {
                listener.onPhotosSuccess(photos)
            }
        }
    }

}