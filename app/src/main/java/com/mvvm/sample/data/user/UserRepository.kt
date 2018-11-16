package com.mvvm.sample.data.user

import android.arch.lifecycle.LiveData
import android.content.Context
import com.mvvm.sample.data.room.User
import com.mvvm.sample.livedata.Event
import com.mvvm.sample.livedata.NetworkRequest
import com.mvvm.sample.data.preference.PreferenceManager
import com.mvvm.sample.webservice.LoginRequest
import com.mvvm.sample.webservice.LoginResponse
import com.mvvm.sample.webservice.RegisterRequest
import com.mvvm.sample.webservice.RegisterResponse

class UserRepository private constructor(
        private val localDataSource: IUserDataSource = UserLocalDataSource(),
        private val remoteDataSource: IUserDataSource = UserRemoteDataSource()) : IUserDataSource {

    companion object {
        @Volatile
        private var INSTANCE: UserRepository? = null

        fun getInstance(): UserRepository = INSTANCE ?: synchronized(this) {
                    INSTANCE ?: UserRepository().also { INSTANCE = it }
        }
    }

    override fun saveUser(context: Context,user: User) = localDataSource.saveUser(context,user)

    override fun getUser(context: Context) = localDataSource.getUser(context)

    override fun login(context: Context, request: LoginRequest): LiveData<Event<LoginResponse>> = object  : NetworkRequest<Event<LoginResponse>>() {

        override fun processBeforeDispatch(response: Event<LoginResponse>) {
            response.peekData()?.let {
                PreferenceManager<String>(context).putPreference(PreferenceManager.ACCESS_TOKEN,it.accessToken)
                localDataSource.saveUser(context, it.toUser())
            }
        }

        override fun networkRequestToObserve(): LiveData<Event<LoginResponse>> = remoteDataSource.login(context, request)

    }.performRequest()

    override fun register(context: Context, request: RegisterRequest): LiveData<Event<RegisterResponse>> = object : NetworkRequest<Event<RegisterResponse>>(){

        override fun processBeforeDispatch(response: Event<RegisterResponse>) {
            response.peekData()?.let {
                PreferenceManager<String>(context).putPreference(PreferenceManager.ACCESS_TOKEN, it.accessToken)
                localDataSource.saveUser(context, it.toUser())
            }
        }

            override fun networkRequestToObserve(): LiveData<Event<RegisterResponse>> = remoteDataSource.register(context, request)

        }.performRequest()

    override fun isLoggedIn(context: Context): Boolean = localDataSource.isLoggedIn(context)

    override fun logout(context: Context) = localDataSource.logout(context)

}
