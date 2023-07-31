package com.example.baroquebeacon.ui.features.overview.model

import com.example.baroquebeacon.domain.model.Artwork

/**
 * Represents a single item in the overview feed.
* */
sealed class OverviewFeedUiItem {
    object LoadingIndicator : OverviewFeedUiItem()
    data class Header(val artistName: String) : OverviewFeedUiItem()
    data class ArtworkPreview(
        val id: String,
        val title: String,
        val imageUrl: String?
    ) : OverviewFeedUiItem()
}

fun Artwork.toArtObjectUiModel(): OverviewFeedUiItem.ArtworkPreview {
    return OverviewFeedUiItem.ArtworkPreview(
        id = id,
        title = title,
        imageUrl = imageUrl
    )
}

fun List<Artwork>.toArtObjectUiModels(): List<OverviewFeedUiItem.ArtworkPreview> {
    return map { it.toArtObjectUiModel() }
}