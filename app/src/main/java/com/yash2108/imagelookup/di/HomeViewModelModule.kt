package com.yash2108.imagelookup.di

import com.yash2108.openissuesreader.database.dao.HomeDao
import com.yash2108.openissuesreader.models.HomeDataSource
import com.yash2108.openissuesreader.models.HomeLocalDataSourceImpl
import com.yash2108.openissuesreader.models.HomeRemoteDataSourceImpl
import com.yash2108.openissuesreader.repositories.HomeRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
abstract class HomeViewModelModule {

    @Binds
    @ViewModelScoped
    @LocalDataSource
    abstract fun bindsHomeLocalDataSource(impl: HomeLocalDataSourceImpl): HomeDataSource


    @Binds
    @ViewModelScoped
    @RemoteDataSource
    abstract fun bindsHomeRemoteDataSource(impl: HomeRemoteDataSourceImpl): HomeDataSource

    companion object {

        @Provides
        @ViewModelScoped
        fun providesHomeRepository(@LocalDataSource localDataSource: HomeDataSource,
                                   @RemoteDataSource remoteDataSource: HomeDataSource,
                                   homeDao: HomeDao): HomeRepository {
            return HomeRepository(localDataSource, remoteDataSource, homeDao)
        }
    }

}