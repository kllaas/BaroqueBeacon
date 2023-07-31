package com.example.baroquebeacon.data.repository

import com.example.baroquebeacon.data.datasource.ArtworkRemoteDataSource
import com.example.baroquebeacon.data.model.ArtworkDetailsDto
import com.example.baroquebeacon.data.model.ArtworkDto
import javax.inject.Inject

class ArtworkRepositoryImpl @Inject constructor(
    private val service: ArtworkRemoteDataSource,
) : ArtworkRepository {

    override suspend fun getArtworksGroupedByArtist(
        page: Int,
        language: String
    ): Map<String, List<ArtworkDto>> {
        val artworks = service.fetchArtworks(page, language).artObjects
        return artworks.groupBy { it.principalOrFirstMaker }
    }

    override suspend fun getArtworkDetails(artworkId: String, language: String): ArtworkDetailsDto {
        return service.fetchArtworkDetails(artworkId, language)
    }
}