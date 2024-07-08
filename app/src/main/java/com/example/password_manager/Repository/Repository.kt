package com.example.password_manager.Repository

import com.example.password_manager.data.PassDao
import com.example.password_manager.data.PassEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class Repository @Inject constructor(
    private val passDao: PassDao
) {

    suspend fun addPassword(passEntity: PassEntity) = passDao.addPassword(passEntity)
    suspend fun updatePassword(passEntity: PassEntity) = passDao.updatePassword(
        password = passEntity.password,
        accountName = passEntity.accountName,
        userName = passEntity.userName,
        id = passEntity.id
    )
    suspend fun deletedPassword(passEntity: PassEntity)= passDao.deletePassword(passEntity)
    fun getAllPassword(): Flow<List<PassEntity>> = passDao.getAllPassword().flowOn(
        Dispatchers.IO).conflate()

}