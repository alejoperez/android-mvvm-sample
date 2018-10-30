package com.mvvm.sample.splash

import com.mvvm.sample.base.IBaseView

interface ISplashContract {

    interface View: IBaseView {
        fun goToNextScreen()
    }

    interface Presenter {
        fun isLoggedIn(): Boolean
    }

}