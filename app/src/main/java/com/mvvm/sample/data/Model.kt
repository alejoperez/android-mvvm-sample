package com.mvvm.sample.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class User(@PrimaryKey val id: Long, val name: String, val email: String)

@Entity
data class Place(@PrimaryKey val id: Long, val companyName: String, val address: String, val lat: Double, val lon: Double)

@Entity
data class Photo(@PrimaryKey val id: Long, val albumId: String, val title: String, val url: String, val thumbnailUrl: String)
