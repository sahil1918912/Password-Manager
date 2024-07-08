package com.example.password_manager.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PassDao {

    @Query("SELECT * FROM passwordManager")
    fun getAllPassword(): Flow<List<PassEntity>>

    @Query("SELECT * FROM passwordManager WHERE id=:id")
    fun getPasswordfromId(id:Int):PassEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPassword(passwordEntity: PassEntity)

    @Query("UPDATE passwordManager SET accountName = :accountName, userName = :userName, password =:password WHERE id =:id")
    suspend fun updatePassword(accountName:String,password:String,userName:String,id:Int)

    @Delete
    suspend fun deletePassword(passwordEntity: PassEntity)


}