package com.mvvm.sample.main

import com.mvvm.sample.base.IBaseView
import com.mvvm.sample.data.User

interface IMainContract {

    interface View: IBaseView

    interface Presenter {
        fun getUser(): User?
        fun logout()
    }

}