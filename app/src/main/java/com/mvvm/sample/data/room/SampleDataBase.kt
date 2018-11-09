package com.mvvm.sample.data.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database( entities = [User::class, Place::class, Photo::class], version = 1)
abstract class SampleDataBase: RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun placeDao(): PlaceDao
    abstract fun photoDao(): PhotoDao

    companion object {

        @Volatile private var INSTANCE: SampleDataBase? = null

        private const val DATABASE_NAME = "sample_database"

        fun getInstance(context: Context): SampleDataBase = INSTANCE
                ?: synchronized(this) {
            INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context, SampleDataBase::class.java, DATABASE_NAME).fallbackToDestructiveMigration().build()
    }
}