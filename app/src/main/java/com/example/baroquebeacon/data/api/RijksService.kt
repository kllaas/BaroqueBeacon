package com.example.baroquebeacon.data.api

import com.example.baroquebeacon.data.model.ArtworkDetailsResponse
import com.example.baroquebeacon.data.model.ArtworkPageDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RijksService {

    @GET("api/{language}/collection")
    suspend fun fetchArtworks(
        @Path(KEY_LANGUAGE) language: String,
        @Query("p") page: Int,
        @Query("ps") limit: Int = PER_PAGE,
        @Query("s") sortBy: String = "artist",
    ): ArtworkPageDto

    @GET("api/{language}/collection/{id}")  // replace with the correct endpoint
    suspend fun fetchArtworkDetails(
        @Path("id") artworkId: String,
        @Path(KEY_LANGUAGE) language: String,
    ): ArtworkDetailsResponse

    private companion object {
        const val PER_PAGE = 10
        const val KEY_LANGUAGE = "language"
    }
}