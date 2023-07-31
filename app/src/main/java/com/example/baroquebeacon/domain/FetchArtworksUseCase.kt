package com.example.baroquebeacon.domain

import com.example.baroquebeacon.common.LocaleProvider
import com.example.baroquebeacon.data.repository.ArtworkRepository
import com.example.baroquebeacon.di.CoroutineModule.DISPATCHER_IO
import com.example.baroquebeacon.domain.model.ArtworkByArtist
import com.example.baroquebeacon.domain.model.toDomain
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Named

abstract class FetchArtworksUseCase(
    ioDispatcher: CoroutineDispatcher
) : BaseUseCase<Int, ArtworkByArtist>(ioDispatcher)

class FetchArtworksUseCaseImpl @Inject constructor(
    private val repository: ArtworkRepository,
    private val localeProvider: LocaleProvider,
    @Named(DISPATCHER_IO) ioDispatcher: CoroutineDispatcher,
) : FetchArtworksUseCase(ioDispatcher) {

    override suspend fun execute(params: Int): ArtworkByArtist {
        return repository.getArtworksGroupedByArtist(
            params, localeProvider.getLanguage()
        ).toDomain()
    }
}