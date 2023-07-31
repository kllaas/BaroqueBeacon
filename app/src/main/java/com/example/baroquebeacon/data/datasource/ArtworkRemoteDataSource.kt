package com.example.baroquebeacon.data.datasource

import com.example.baroquebeacon.data.model.ArtworkDetailsDto
import com.example.baroquebeacon.data.model.ArtworkPageDto

interface ArtworkRemoteDataSource {

    suspend fun fetchArtworks(page: Int, language: String): ArtworkPageDto

    suspend fun fetchArtworkDetails(artworkId: String, language: String): ArtworkDetailsDto
}