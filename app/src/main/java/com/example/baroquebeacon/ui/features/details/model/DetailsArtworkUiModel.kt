package com.example.baroquebeacon.ui.features.details.model

import com.example.baroquebeacon.domain.model.ArtworkDetails

data class ArtworkDetailsUiModel(
    val id: String,
    val title: String,
    val artist: String,
    val description: String,
    val imageUrl: String?,
)

fun ArtworkDetails.toUiModel() = ArtworkDetailsUiModel(
    id = id,
    title = title,
    artist = artist,
    description = description,
    imageUrl = imageUrl,
)