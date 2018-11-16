package com.mvvm.sample.webservice

import android.arch.lifecycle.LiveData
import com.mvvm.sample.data.room.Photo
import com.mvvm.sample.data.room.Place
import com.mvvm.sample.livedata.Event
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface IApi {

    @POST("user/login")
    fun login(@Body request: LoginRequest): LiveData<Event<LoginResponse>>

    @POST("user/register")
    fun register(@Body request: RegisterRequest): LiveData<Event<RegisterResponse>>

    @GET("places")
    fun getPlaces(): LiveData<Event<List<Place>>>

    @GET("photos")
    fun getPhotos(): LiveData<Event<List<Photo>>>

}