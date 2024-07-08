package com.example.password_manager.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity("passwordManager")
data class PassEntity(
    @ColumnInfo("accountName")
    val accountName: String,
    @ColumnInfo("userName")
    val userName: String,
    @ColumnInfo("password")
    val password: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)