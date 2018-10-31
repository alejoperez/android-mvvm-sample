package com.mvvm.sample.splash

import android.os.Bundle
import android.os.Handler
import com.mvvm.sample.R
import com.mvvm.sample.base.BaseActivity
import com.mvvm.sample.livedata.EventObserver
import com.mvvm.sample.main.MainActivity
import com.mvvm.sample.register.RegisterActivity
import org.jetbrains.anko.startActivity

private const val SPLASH_DELAY = 2000L

class SplashActivity : BaseActivity() {

    private val viewModel by lazy { obtainViewModel(SplashViewModel::class.java) }

    private val isUserLoggedObserver = EventObserver<Boolean> { goToNextScreen(it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({ checkIfUserLoggedIn() }, SPLASH_DELAY)
    }

    private fun checkIfUserLoggedIn() {
        viewModel.isUserLoggedEvent.observe(this, isUserLoggedObserver)
        viewModel.isUserLoggedIn(getViewContext())
    }

    private fun goToNextScreen(isUserLogged: Boolean) {
        if (isUserLogged) {
            startActivity<MainActivity>()
        } else {
            startActivity<RegisterActivity>()
        }
        finish()
    }

}
