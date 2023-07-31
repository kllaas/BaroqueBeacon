package com.example.baroquebeacon.data.datasource

import com.example.baroquebeacon.data.api.RijksService
import com.example.baroquebeacon.data.model.ArtworkDetailsDto
import com.example.baroquebeacon.data.model.ArtworkPageDto
import javax.inject.Inject

class ArtworkRemoteDataSourceImpl @Inject constructor(
    private val service: RijksService,
) : ArtworkRemoteDataSource {

    override suspend fun fetchArtworks(
        page: Int,
        language: String
    ): ArtworkPageDto {
        return service.fetchArtworks(language, page);
    }

    override suspend fun fetchArtworkDetails(artworkId: String, language: String): ArtworkDetailsDto {
        return service.fetchArtworkDetails(artworkId, language).artObject
    }
}