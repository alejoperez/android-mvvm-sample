package com.mvvm.sample.splash

import com.mvvm.sample.data.user.UserRepository

class SplashPresenter(private val view: ISplashContract.View): ISplashContract.Presenter {

    override fun isLoggedIn(): Boolean = UserRepository.getInstance().isLoggedIn(view.getViewContext())

}