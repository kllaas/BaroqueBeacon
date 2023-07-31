package com.example.baroquebeacon.di

import com.example.baroquebeacon.data.datasource.ArtworkRemoteDataSource
import com.example.baroquebeacon.data.datasource.ArtworkRemoteDataSourceImpl
import com.example.baroquebeacon.data.repository.ArtworkRepository
import com.example.baroquebeacon.data.repository.ArtworkRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindArtworkRepository(
        impl: ArtworkRepositoryImpl
    ): ArtworkRepository

    @Binds
    abstract fun bindArtworkRemoteDataSource(
        impl: ArtworkRemoteDataSourceImpl
    ): ArtworkRemoteDataSource
}