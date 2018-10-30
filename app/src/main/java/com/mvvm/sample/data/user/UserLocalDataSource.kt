package com.mvvm.sample.data.user

import android.content.Context
import com.mvvm.sample.data.User
import com.mvvm.sample.storage.PreferenceManager
import com.mvvm.sample.webservice.LoginRequest
import com.mvvm.sample.webservice.RegisterRequest
import io.realm.Realm

class UserLocalDataSource : IUserDataSource {

    override fun isLoggedIn(context: Context): Boolean = PreferenceManager<String>(context).findPreference(PreferenceManager.ACCESS_TOKEN,"").isNotEmpty()

    override fun getUser(): User? = Realm.getDefaultInstance().where(User::class.java).findFirst()

    override fun saveUser(user: User) {
        Realm.getDefaultInstance().executeTransactionAsync{
            realm -> realm.insertOrUpdate(user)
        }
    }

    override fun logout(context: Context) = PreferenceManager<String>(context).putPreference(PreferenceManager.ACCESS_TOKEN,"")

    override fun login(context: Context, request: LoginRequest, listener: UserRepository.ILoginListener) = throw UnsupportedOperationException()

    override fun register(context: Context, request: RegisterRequest, listener: UserRepository.IRegisterListener) = throw UnsupportedOperationException()

}