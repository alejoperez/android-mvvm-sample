package com.mvvm.sample.data.user

import android.arch.lifecycle.LiveData
import android.content.Context
import com.mvvm.sample.data.room.User
import com.mvvm.sample.livedata.DataResource
import com.mvvm.sample.webservice.LoginRequest
import com.mvvm.sample.webservice.LoginResponse
import com.mvvm.sample.webservice.RegisterRequest
import com.mvvm.sample.webservice.RegisterResponse

interface IUserDataSource {
    fun getUser(context: Context): LiveData<DataResource<User>>
    fun saveUser(context: Context,user: User)
    fun login(context: Context, request: LoginRequest): LiveData<DataResource<LoginResponse>>
    fun register(context: Context, request: RegisterRequest): LiveData<DataResource<RegisterResponse>>
    fun isLoggedIn(context: Context): Boolean
    fun logout(context: Context)
}