package com.example.baroquebeacon.di

import com.example.baroquebeacon.di.CoroutineModule.DISPATCHER_IO
import com.example.baroquebeacon.domain.FetchArtworksUseCase
import com.example.baroquebeacon.ui.features.overview.ArtworkPageMerger
import com.example.baroquebeacon.ui.features.overview.OverviewViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    fun provideOverviewViewModel(
        repository: FetchArtworksUseCase,
        merger: ArtworkPageMerger,
        @Named(DISPATCHER_IO) ioDispatcher: CoroutineDispatcher
    ): OverviewViewModel {
        return OverviewViewModel(repository, merger, ioDispatcher)
    }
}