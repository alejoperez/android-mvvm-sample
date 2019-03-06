package com.mvvm.sample.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Query("SELECT * from user LIMIT 1")
    fun getUser(): LiveData<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveUser(user: User)

}

@Dao
interface PlaceDao {

    @Query("SELECT * from place")
    fun getPlaces(): LiveData<List<Place>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun savePlaces(places: List<Place>)

}

@Dao
interface PhotoDao {

    @Query("SELECT * from photo")
    fun getPhotos(): LiveData<List<Photo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun savePhotos(photos: List<Photo>)
}