package com.yash2108.imagelookup.di

import android.content.Context
import androidx.room.Room
import com.yash2108.openissuesreader.database.AppDatabase
import com.yash2108.openissuesreader.database.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, Constants.DATABASE_NAME)
            .build()
    }

    @Singleton
    @Provides
    fun providesHomeDao(database: AppDatabase) = database.homeDao()

}