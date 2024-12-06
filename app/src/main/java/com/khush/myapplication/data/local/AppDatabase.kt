package com.khush.myapplication.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.khush.myapplication.data.local.model.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDao(): AppDao
}