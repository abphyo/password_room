package com.example.room1.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [User::class, Data::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getDao(): Dao
}