package com.khush.myapplication.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.khush.myapplication.data.local.model.UserEntity

@Dao
interface AppDao {

    @Insert
    suspend fun insert(userEntity: UserEntity)

    @Query("SELECT * FROM UserEntity")
    fun getAll(): List<UserEntity>
}