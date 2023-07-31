package com.example.baroquebeacon.data.model

data class ArtworkPageDto(
    val page: Int,
    val artObjects: List<ArtworkDto>
)

data class ArtworkDto(
    val objectNumber: String,
    val title: String,
    val principalOrFirstMaker: String,
    val longTitle: String,
    val webImage: WebImageDto?,
    val headerImage: HeaderImageDto?
)

data class WebImageDto(val url: String)
data class HeaderImageDto(val url: String)