package com.mvvm.sample.data.photos

import android.content.Context
import com.mvvm.sample.data.Photo
import com.mvvm.sample.extensions.enqueue
import com.mvvm.sample.webservice.IApi
import com.mvvm.sample.webservice.WebService
import java.lang.UnsupportedOperationException

class PhotosRemoteDataSource : IPhotosDataSource {

    override fun savePhotos(photos: List<Photo>) = throw UnsupportedOperationException()

    override fun getPhotos(context: Context, listener: PhotosRepository.IPhotosListener) {
        val service = WebService.createService(context, IApi::class.java)
        val call = service.getPhotos()
        call.enqueue(
                { response ->
                    if (response.isSuccessful) {
                        listener.onPhotosSuccess(response.body())

                    } else {
                        listener.onPhotosFailure()
                    }
                },
                {
                    listener.onNetworkError()
                }
        )
    }

}