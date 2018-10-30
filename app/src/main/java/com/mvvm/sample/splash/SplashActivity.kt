package com.mvvm.sample.splash

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Handler
import com.mvvm.sample.R
import com.mvvm.sample.base.BaseActivity
import com.mvvm.sample.main.MainActivity
import com.mvvm.sample.register.RegisterActivity
import org.jetbrains.anko.startActivity

private const val SPLASH_DELAY = 2000L

class SplashActivity : BaseActivity() {

    private val viewModel by lazy { ViewModelProviders.of(this).get(SplashViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({ goToNextScreen() }, SPLASH_DELAY)
    }

    private fun goToNextScreen() {
        viewModel.isUserLoggedEvent.observe(this, isUserLoggedObserver)
        viewModel.isUserLoggedIn(this)
    }

    private val isUserLoggedObserver = Observer<Boolean> { it ->
        it?.let {
            if (it) {
                startActivity<MainActivity>()
            } else {
                startActivity<RegisterActivity>()
            }
            finish()
        }
    }

}
