package com.example.baroquebeacon.di

import com.example.baroquebeacon.domain.FetchArtworkDetailUseCase
import com.example.baroquebeacon.domain.FetchArtworkDetailUseCaseImpl
import com.example.baroquebeacon.domain.FetchArtworksUseCase
import com.example.baroquebeacon.domain.FetchArtworksUseCaseImpl
import com.example.baroquebeacon.ui.features.overview.ArtworkPageMerger
import com.example.baroquebeacon.ui.features.overview.ArtworkPageMergerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class DomainModule {

    @Binds
    abstract fun bindFetchArtworksUseCase(
        impl: FetchArtworksUseCaseImpl
    ): FetchArtworksUseCase

    @Binds
    abstract fun bindFetchArtworkDetailUseCase(
        impl: FetchArtworkDetailUseCaseImpl
    ): FetchArtworkDetailUseCase

    @Binds
    abstract fun bindArtworkPageMerger(
        impl: ArtworkPageMergerImpl
    ): ArtworkPageMerger
}