package com.example.baroquebeacon.domain.model

import com.example.baroquebeacon.data.model.ArtworkDto

typealias ArtworkByArtist = Map<String, List<Artwork>>

data class Artwork(
    val id: String,
    val title: String,
    val artist: String,
    val description: String,
    val imageUrl: String?,
    val headerImageUrl: String?,
)

fun ArtworkDto.toDomain() = Artwork(
    id = objectNumber,
    title = title,
    artist = principalOrFirstMaker,
    description = longTitle,
    imageUrl = webImage?.url,
    headerImageUrl = headerImage?.url,
)

fun Map<String, List<ArtworkDto>>.toDomain(): ArtworkByArtist {
    return mapValues { (_, artworks) ->
        artworks.map { it.toDomain() }
    }
}