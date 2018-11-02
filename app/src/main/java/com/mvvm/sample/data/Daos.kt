package com.mvvm.sample.data

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

@Dao
interface UserDao {

    @Query("SELECT * from user LIMIT 1")
    fun getUser(): User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveUser(user: User)

}

@Dao
interface PlaceDao {

    @Query("SELECT * from place")
    fun getPlaces(): List<Place>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun savePlaces(places: List<Place>)

}

@Dao
interface PhotoDao {

    @Query("SELECT * from photo")
    fun getPhotos(): List<Photo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun savePhotos(photos: List<Photo>)
}