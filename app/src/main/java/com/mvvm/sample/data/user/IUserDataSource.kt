package com.mvvm.sample.data.user

import androidx.lifecycle.LiveData
import android.content.Context
import com.mvvm.sample.data.room.User
import com.mvvm.sample.livedata.Event
import com.mvvm.sample.webservice.LoginRequest
import com.mvvm.sample.webservice.LoginResponse
import com.mvvm.sample.webservice.RegisterRequest
import com.mvvm.sample.webservice.RegisterResponse

interface IUserDataSource {
    fun getUser(context: Context): LiveData<Event<User>>
    fun saveUser(context: Context,user: User)
    fun login(context: Context, request: LoginRequest): LiveData<Event<LoginResponse>>
    fun register(context: Context, request: RegisterRequest): LiveData<Event<RegisterResponse>>
    fun isLoggedIn(context: Context): Boolean
    fun logout(context: Context)
}