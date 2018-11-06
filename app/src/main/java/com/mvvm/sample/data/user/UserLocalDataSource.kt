package com.mvvm.sample.data.user

import android.content.Context
import com.mvvm.sample.data.SampleDataBase
import com.mvvm.sample.data.User
import com.mvvm.sample.storage.PreferenceManager
import com.mvvm.sample.webservice.LoginRequest
import com.mvvm.sample.webservice.RegisterRequest
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class UserLocalDataSource : IUserDataSource {

    override fun isLoggedIn(context: Context): Boolean = PreferenceManager<String>(context).findPreference(PreferenceManager.ACCESS_TOKEN,"").isNotEmpty()

    override fun getUser(context: Context, listener: UserRepository.IUserListener)  {
        doAsync {
            val user = SampleDataBase.getInstance(context).userDao().getUser()
            uiThread {
                listener.onUserSuccess(user)
            }
        }
    }

    override fun saveUser(context: Context, user: User) {
        doAsync {
            SampleDataBase.getInstance(context).userDao().saveUser(user)
        }
    }

    override fun logout(context: Context) = PreferenceManager<String>(context).putPreference(PreferenceManager.ACCESS_TOKEN,"")

    override fun login(context: Context, request: LoginRequest, listener: UserRepository.ILoginListener) = throw UnsupportedOperationException()

    override fun register(context: Context, request: RegisterRequest, listener: UserRepository.IRegisterListener) = throw UnsupportedOperationException()

}