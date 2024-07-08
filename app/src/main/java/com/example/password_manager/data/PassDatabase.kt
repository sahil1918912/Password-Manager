package com.example.password_manager.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [PassEntity::class],
    version = 1,
    exportSchema = false

)
abstract class PassDatabase: RoomDatabase() {
    abstract fun PassDao():PassDao
}
