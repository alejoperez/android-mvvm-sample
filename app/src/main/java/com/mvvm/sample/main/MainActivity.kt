package com.mvvm.sample.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import com.mvvm.sample.base.BaseActivity
import com.mvvm.sample.R
import com.mvvm.sample.data.User
import com.mvvm.sample.photos.PhotosFragment
import com.mvvm.sample.places.PlacesFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val viewModel by lazy { ViewModelProviders.of(this).get(MainViewModel::class.java) }

    private val onLogoutSuccessObserver = Observer<Unit> {
        finishAffinity()
    }

    private val userObserver = Observer<User> {
        val headerView = navView.getHeaderView(0)
        val textViewUserName = headerView.findViewById(R.id.tvUserName) as TextView
        val textViewUserEmail = headerView.findViewById(R.id.tvUserEmail) as TextView
        textViewUserName.text = it?.name
        textViewUserEmail.text = it?.email
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setToolbarTitle(R.string.app_name)
        initViewModel()
        initView()
        replaceFragment(PlacesFragment.newInstance(),R.id.main_content_view, PlacesFragment.TAG)
    }

    private fun initViewModel() {
        viewModel.user.observe(this, userObserver)
        viewModel.onLogoutSuccess.observe(this, onLogoutSuccessObserver)
    }

    private fun initView() {
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)
        navView.menu.getItem(0).isChecked = true

        viewModel.getUser()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_places -> {
                replaceFragment(PlacesFragment.newInstance(),R.id.main_content_view, PlacesFragment.TAG)
            }
            R.id.nav_photos -> {
                replaceFragment(PhotosFragment.newInstance(),R.id.main_content_view, PhotosFragment.TAG)
            }
            R.id.nav_logout -> {
                viewModel.logout(this)
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
