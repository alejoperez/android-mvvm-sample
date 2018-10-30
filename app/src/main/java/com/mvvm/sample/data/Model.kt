package com.mvvm.sample.data

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class User(@PrimaryKey var id: Long = 0L,
                var name: String = "",
                var email: String = ""): RealmObject()

open class Place(@PrimaryKey var id: Long = 0L,
            var companyName: String = "",
            var address: String = "",
            var lat: Double = 0.0,
            var lon: Double = 0.0): RealmObject()


open class Photo(@PrimaryKey var id: Long = 0L,
            var albumId: String = "",
            var title: String = "",
            var url: String = "",
            var thumbnailUrl: String = ""): RealmObject()
