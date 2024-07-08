package com.example.password_manager.di

import android.content.Context
import androidx.room.Room
import com.example.password_manager.data.PassDao
import com.example.password_manager.data.PassDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideTodoDao(passwordDatabase: PassDatabase): PassDao = passwordDatabase.PassDao()

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context) :PassDatabase =
        Room.databaseBuilder(
            context,PassDatabase::class.java,
            "passwordManager"
        ).fallbackToDestructiveMigration().build()


}