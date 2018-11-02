package com.mvvm.sample.data.user

import android.content.Context
import com.mvvm.sample.data.IBaseSourceListener
import com.mvvm.sample.data.User
import com.mvvm.sample.storage.PreferenceManager
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

    override fun getUser(context: Context): User? = localDataSource.getUser(context)

    override fun login(context: Context, request: LoginRequest, listener: ILoginListener) {
        remoteDataSource.login(context, request, object : ILoginListener {
            override fun onLoginSuccess(response: LoginResponse?) {
                response?.let {
                    PreferenceManager<String>(context).putPreference(PreferenceManager.ACCESS_TOKEN,response.accessToken)
                    localDataSource.saveUser(context, response.toUser())
                    listener.onLoginSuccess(response)
                }
            }

            override fun onLoginFailure() = listener.onLoginFailure()

            override fun onNetworkError() = listener.onNetworkError()
        })
    }

    override fun register(context: Context, request: RegisterRequest, listener: IRegisterListener) {
        remoteDataSource.register(context, request, object : IRegisterListener {
            override fun onRegisterSuccess(response: RegisterResponse?) {
                response?.let {
                    PreferenceManager<String>(context).putPreference(PreferenceManager.ACCESS_TOKEN, response.accessToken)
                    localDataSource.saveUser(context, response.toUser())
                    listener.onRegisterSuccess(response)
                }
            }

            override fun onRegisterFailure() = listener.onRegisterFailure()

            override fun onNetworkError() = listener.onNetworkError()
        })
    }

    override fun isLoggedIn(context: Context): Boolean = localDataSource.isLoggedIn(context)

    override fun logout(context: Context) = localDataSource.logout(context)

    interface IRegisterListener : IBaseSourceListener {
        fun onRegisterSuccess(response: RegisterResponse?)
        fun onRegisterFailure()
    }

    interface ILoginListener : IBaseSourceListener {
        fun onLoginSuccess(response: LoginResponse?)
        fun onLoginFailure()
    }

}
