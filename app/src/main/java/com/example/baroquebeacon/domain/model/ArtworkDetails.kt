package com.example.baroquebeacon.domain.model

import com.example.baroquebeacon.data.model.ArtworkDetailsDto

data class ArtworkDetails(
    val id: String,
    val title: String,
    val artist: String,
    val description: String,
    val imageUrl: String?,
)

fun ArtworkDetailsDto.toDomain() = ArtworkDetails(
    id = objectNumber,
    title = title,
    artist = principalOrFirstMaker,
    description = description,
    imageUrl = webImage?.url,
)
