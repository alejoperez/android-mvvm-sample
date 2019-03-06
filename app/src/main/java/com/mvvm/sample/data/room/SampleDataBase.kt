package com.mvvm.sample.data.room

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context

@Database( entities = [User::class, Place::class, Photo::class], version = 1, exportSchema = false)
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