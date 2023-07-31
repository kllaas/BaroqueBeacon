package com.example.baroquebeacon.ui.features.overview

import com.example.baroquebeacon.domain.model.Artwork
import com.example.baroquebeacon.ui.features.overview.model.OverviewFeedUiItem
import com.example.baroquebeacon.ui.features.overview.model.toArtObjectUiModels
import java.util.LinkedList
import javax.inject.Inject

interface ArtworkPageMerger {

    fun merge(
        currentData: List<OverviewFeedUiItem>,
        newData: Map<String, List<Artwork>>,
    ): List<OverviewFeedUiItem>
}

/**
 * This class is responsible for merging the current data with the new page.
 * It manages the loading indicator removal and the header insertion.
 */
class ArtworkPageMergerImpl @Inject constructor() : ArtworkPageMerger {

    override fun merge(
        currentData: List<OverviewFeedUiItem>,
        newData: Map<String, List<Artwork>>,
    ): List<OverviewFeedUiItem> {
        val result = LinkedList(currentData)
        // Find the last artist name in the current data.
        var previousArtist = currentData.findLast { it is OverviewFeedUiItem.Header }?.let {
            (it as OverviewFeedUiItem.Header).artistName
        }

        // Remove loading indicator if it exists.
        if (result.lastOrNull() == OverviewFeedUiItem.LoadingIndicator) {
            result.removeLast()
        }

        for ((artist, artworks) in newData) {
            if (artist == previousArtist) {
                result.addAll(artworks.toArtObjectUiModels())
            } else {
                result.add(OverviewFeedUiItem.Header(artist))
                result.addAll(artworks.toArtObjectUiModels())
                previousArtist = artist
            }
        }

        return result
    }
}