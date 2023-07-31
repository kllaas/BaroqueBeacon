@file:OptIn(ExperimentalFoundationApi::class)

package com.example.baroquebeacon.ui.features.overview.composable

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.baroquebeacon.R
import com.example.baroquebeacon.ui.features.common.UiState
import com.example.baroquebeacon.ui.features.common.composable.ErrorScreen
import com.example.baroquebeacon.ui.features.overview.OverviewViewModel
import com.example.baroquebeacon.ui.features.overview.composable.Constants.LOAD_MORE_THRESHOLD
import com.example.baroquebeacon.ui.features.overview.model.OverviewFeedUiItem
import kotlin.math.ceil

@Composable
fun OverviewScreen(
    navigateToDetails: (String) -> Unit
) {
    val viewModel: OverviewViewModel = viewModel()
    val artworksState: UiState<List<OverviewFeedUiItem>> by viewModel.artworks.collectAsState()

    Column {
        OverviewAppBar()

        when (artworksState) {
            is UiState.Loading -> LoadingUI()
            is UiState.Success -> OverviewFeed(
                artworksState as UiState.Success<List<OverviewFeedUiItem>>,
                loadMore = { viewModel.fetchArtworks() },
                onItemClicked = { navigateToDetails(it) }
            )

            is UiState.Error -> ErrorScreen(
                message = (artworksState as UiState.Error).message,
                onRetry = { viewModel.reload() }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OverviewAppBar() {
    TopAppBar(
        modifier = Modifier.shadow(16.dp),
        title = {
            Text(text = stringResource(R.string.app_name))
        },
    )
}

@Composable
fun LoadingUI() {
    Box(
        modifier = Modifier.fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
    }
}


@Composable
fun OverviewFeed(
    uiState: UiState.Success<List<OverviewFeedUiItem>>,
    loadMore: () -> Unit,
    onItemClicked: (String) -> Unit = {}
) {
    val data = uiState.data

    // Detecting if we need to trigger load more
    val triggerLoadMorePosition = ceil(data.size * LOAD_MORE_THRESHOLD).toInt()

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        data.forEachIndexed { index, item ->
            when (item) {
                is OverviewFeedUiItem.Header -> {
                    stickyHeader {
                        Header(item.artistName)
                    }
                }

                is OverviewFeedUiItem.ArtworkPreview -> {
                    item {
                        ArtworkCard(item) { onItemClicked(item.id) }
                    }
                    if (index >= triggerLoadMorePosition) {
                        loadMore()
                    }
                }

                is OverviewFeedUiItem.LoadingIndicator -> {
                    item {
                        LoadingUI()
                    }
                }
            }
        }
    }
}

@Composable
fun Header(artistName: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 8.dp)
            .background(MaterialTheme.colorScheme.background)
            .padding(8.dp)
    ) {
        Text(text = artistName, fontSize = 18.sp)
    }
}

@Composable
fun ArtworkCard(artObject: OverviewFeedUiItem.ArtworkPreview, onClick: (String) -> Unit) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(8.dp)
            .clickable { onClick(artObject.id) }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            artObject.imageUrl?.let {
                AsyncImage(
                    model = it,
                    contentDescription = artObject.title,
                    modifier = Modifier.size(100.dp),
                    contentScale = ContentScale.Crop
                )
            }

            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Text(text = artObject.title, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

private object Constants {
    const val LOAD_MORE_THRESHOLD = 0.8
}