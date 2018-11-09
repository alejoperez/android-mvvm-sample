package com.mvvm.sample.webservice

import android.arch.lifecycle.LiveData
import com.mvvm.sample.data.room.Photo
import com.mvvm.sample.data.room.Place
import com.mvvm.sample.livedata.DataResource
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface IApi {

    @POST("user/login")
    fun login(@Body request: LoginRequest): LiveData<DataResource<LoginResponse>>

    @POST("user/register")
    fun register(@Body request: RegisterRequest): LiveData<DataResource<RegisterResponse>>

    @GET("places")
    fun getPlaces(): LiveData<DataResource<List<Place>>>

    @GET("photos")
    fun getPhotos(): LiveData<DataResource<List<Photo>>>

}