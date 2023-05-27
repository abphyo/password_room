package com.example.room1.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface Dao {
    @Insert
    suspend fun insert(user: User)

    @Query("SELECT COUNT(*) FROM users WHERE username = :username")
    suspend fun checkIfNameExists(username: String): Int

    @Query("SELECT * FROM users WHERE username = :username")
    suspend fun getByUsername(username: String): User?

    @Insert
    suspend fun insertData(data: Data)

    @Query("SELECT * FROM data WHERE `key` = :key ")
    suspend fun getListByKey(key: String?): List<Data?>
}