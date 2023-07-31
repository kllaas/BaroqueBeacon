package com.example.baroquebeacon.domain

import com.example.baroquebeacon.common.LocaleProvider
import com.example.baroquebeacon.data.repository.ArtworkRepository
import com.example.baroquebeacon.di.CoroutineModule
import com.example.baroquebeacon.domain.model.ArtworkDetails
import com.example.baroquebeacon.domain.model.toDomain
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Named

abstract class FetchArtworkDetailUseCase(
    ioDispatcher: CoroutineDispatcher
) : BaseUseCase<String, ArtworkDetails>(ioDispatcher)

class FetchArtworkDetailUseCaseImpl @Inject constructor(
    private val repository: ArtworkRepository,
    private val localeProvider: LocaleProvider,
    @Named(CoroutineModule.DISPATCHER_IO) ioDispatcher: CoroutineDispatcher,
) : FetchArtworkDetailUseCase(ioDispatcher) {

    override suspend fun execute(artworkId: String): ArtworkDetails {
        return repository.getArtworkDetails(
            artworkId, localeProvider.getLanguage()
        ).toDomain()
    }
}