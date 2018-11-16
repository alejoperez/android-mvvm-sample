package com.mvvm.sample.splash

import android.os.Bundle
import android.os.Handler
import com.mvvm.sample.R
import com.mvvm.sample.base.BaseActivity
import com.mvvm.sample.databinding.ActivitySplashBinding
import com.mvvm.sample.livedata.EventObserver
import com.mvvm.sample.main.MainActivity
import com.mvvm.sample.register.RegisterActivity
import org.jetbrains.anko.startActivity

private const val SPLASH_DELAY = 2000L

class SplashActivity : BaseActivity<SplashViewModel,ActivitySplashBinding>() {

    override fun getLayoutId(): Int = R.layout.activity_splash
    override fun getViewModelClass(): Class<SplashViewModel> = SplashViewModel::class.java
    override fun getVariablesToBind(): Map<Int, Any> = emptyMap()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Handler().postDelayed({ checkIfUserLoggedIn() }, SPLASH_DELAY)
    }

    private fun checkIfUserLoggedIn() {
        viewModel.isUserLoggedEvent.observe(this, isUserLoggedObserver)
        viewModel.isUserLoggedIn()
    }

    private val isUserLoggedObserver = EventObserver<Boolean> { goToNextScreen(it) }

    private fun goToNextScreen(isUserLogged: Boolean) {
        if (isUserLogged) {
            startActivity<MainActivity>()
        } else {
            startActivity<RegisterActivity>()
        }
        finish()
    }

}
