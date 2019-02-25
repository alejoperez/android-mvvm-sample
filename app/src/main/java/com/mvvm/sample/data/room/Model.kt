package com.mvvm.sample.data.room

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class User(@PrimaryKey val id: Long, val name: String, val email: String) : Parcelable

@Parcelize
@Entity
data class Place(@PrimaryKey val id: Long, val companyName: String, val address: String, val lat: Double, val lon: Double) : Parcelable

@Parcelize
@Entity
data class Photo(@PrimaryKey val id: Long, val albumId: String, val title: String, val url: String, val thumbnailUrl: String) : Parcelable
