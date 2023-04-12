package com.example.pairmatch.di

import android.app.Application
import androidx.room.Room
import com.example.pairmatch.data.AppDatabase
import com.example.pairmatch.data.RepositoryImpl
import com.example.pairmatch.domain.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

object AppModule {
    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): AppDatabase {

        return  AppDatabase.getInstance(app)
//
    }


    @Singleton
    @Provides
    fun provideYourDao(db: AppDatabase) = db.appDao

    @Singleton
    @Provides
    fun provideRepository(db: AppDatabase): Repository {
        return RepositoryImpl(db.appDao)
    }

}