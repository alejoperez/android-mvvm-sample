package com.mvvm.sample.data.user

import androidx.lifecycle.LiveData
import android.content.Context
import com.mvvm.sample.data.room.SampleDataBase
import com.mvvm.sample.data.room.User
import com.mvvm.sample.livedata.DataRequest
import com.mvvm.sample.livedata.Event
import com.mvvm.sample.data.preference.PreferenceManager
import com.mvvm.sample.webservice.LoginRequest
import com.mvvm.sample.webservice.LoginResponse
import com.mvvm.sample.webservice.RegisterRequest
import com.mvvm.sample.webservice.RegisterResponse
import org.jetbrains.anko.doAsync

class UserLocalDataSource : IUserDataSource {

    override fun isLoggedIn(context: Context): Boolean = PreferenceManager<String>(context).findPreference(PreferenceManager.ACCESS_TOKEN,"").isNotEmpty()

    override fun getUser(context: Context): LiveData<Event<User>> = object : DataRequest<User>() {
        override fun dataRequestToObserve(): LiveData<User> = SampleDataBase.getInstance(context).userDao().getUser()
    }.performRequest()

    override fun saveUser(context: Context, user: User) {
        doAsync {
            SampleDataBase.getInstance(context).userDao().saveUser(user)
        }
    }

    override fun logout(context: Context) = PreferenceManager<String>(context).putPreference(PreferenceManager.ACCESS_TOKEN,"")

    override fun login(context: Context, request: LoginRequest): LiveData<Event<LoginResponse>> = throw UnsupportedOperationException()

    override fun register(context: Context, request: RegisterRequest): LiveData<Event<RegisterResponse>> = throw UnsupportedOperationException()

}