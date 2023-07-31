package com.example.baroquebeacon.ui.features.overview

import com.example.baroquebeacon.domain.model.Artwork
import com.example.baroquebeacon.ui.features.overview.model.OverviewFeedUiItem
import junit.framework.TestCase.assertEquals
import org.junit.Test

class ArtworkPageMergerTest {

    private val merger = ArtworkPageMergerImpl()

    @Test
    fun `merge removes loading indicator and appends new data`() {
        val currentData = listOf(
            OverviewFeedUiItem.Header("Artist1"),
            OverviewFeedUiItem.LoadingIndicator
        )

        val newData = mapOf(
            "Artist1" to listOf(
                Artwork("1", "Title1", "Artist1", "Description1", null, null)
            )
        )

        val expected = listOf(
            OverviewFeedUiItem.Header("Artist1"),
            OverviewFeedUiItem.ArtworkPreview("1", "Title1", null)
        )

        val result = merger.merge(currentData, newData)

        assertEquals(expected, result)
    }

    @Test
    fun `merge adds new artist header if artist is different`() {
        val currentData = listOf(
            OverviewFeedUiItem.Header("Artist1")
        )

        val newData = mapOf(
            "Artist2" to listOf(
                Artwork("2", "Title2", "Artist2", "Description2", null, null)
            )
        )

        val expected = listOf(
            OverviewFeedUiItem.Header("Artist1"),
            OverviewFeedUiItem.Header("Artist2"),
            OverviewFeedUiItem.ArtworkPreview("2", "Title2", null)
        )

        val result = merger.merge(currentData, newData)

        assertEquals(expected, result)
    }

    @Test
    fun `merge appends to existing artist if artist is the same`() {
        val currentData = listOf(
            OverviewFeedUiItem.Header("Artist1")
        )

        val newData = mapOf(
            "Artist1" to listOf(
                Artwork("1", "Title1", "Artist1", "Description1", null, null)
            )
        )

        val expected = listOf(
            OverviewFeedUiItem.Header("Artist1"),
            OverviewFeedUiItem.ArtworkPreview("1", "Title1", null)
        )

        val result = merger.merge(currentData, newData)

        assertEquals(expected, result)
    }

}