package com.example.room1.room

import android.app.Application
import androidx.room.Room

class RoomApplication: Application() {

//    companion object {
//        lateinit var database: AppDatabase
//    }
//
//    override fun onCreate() {
//        super.onCreate()
//        database = Room.databaseBuilder(this, AppDatabase::class.java, "database.db").allowMainThreadQueries().build()
//    }

    val database: AppDatabase by lazy {
        Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "database.db"
        ).build()
    }
}