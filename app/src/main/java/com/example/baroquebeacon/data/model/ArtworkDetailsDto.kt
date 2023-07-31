package com.example.baroquebeacon.data.model

data class ArtworkDetailsResponse(
    val artObject: ArtworkDetailsDto
)

data class ArtworkDetailsDto(
    val objectNumber: String,
    val title: String,
    val principalOrFirstMaker: String,
    val description: String,
    val webImage: WebImageDto?,
)
