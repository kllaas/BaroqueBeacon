package com.example.baroquebeacon.data.repository

import com.example.baroquebeacon.data.model.ArtworkDetailsDto
import com.example.baroquebeacon.data.model.ArtworkDto

interface ArtworkRepository {

    suspend fun getArtworksGroupedByArtist(page: Int, language: String): Map<String, List<ArtworkDto>>

    suspend fun getArtworkDetails(artworkId: String, language: String): ArtworkDetailsDto
}