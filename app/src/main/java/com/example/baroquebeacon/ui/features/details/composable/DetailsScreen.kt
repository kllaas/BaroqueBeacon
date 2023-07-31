package com.example.baroquebeacon.ui.features.details.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.baroquebeacon.R
import com.example.baroquebeacon.ui.features.common.UiState
import com.example.baroquebeacon.ui.features.common.composable.ErrorScreen
import com.example.baroquebeacon.ui.features.details.DetailsViewModel
import com.example.baroquebeacon.ui.features.details.model.ArtworkDetailsUiModel

@Composable
fun DetailsScreen(onBackPressed: () -> Unit) {
    val viewModel: DetailsViewModel = viewModel()
    val artworksState by viewModel.artworkDetailState.collectAsState()

    ArtworkDetailScreen(
        artworkState = artworksState,
        onRetry = { viewModel.fetchDetails() },
        onBackPressed = onBackPressed
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtworkDetailScreen(
    artworkState: UiState<ArtworkDetailsUiModel>,
    onRetry: () -> Unit,
    onBackPressed: () -> Unit
) {
    Column {
        TopAppBar(
            title = { Text(text = stringResource(R.string.app_name)) },
            navigationIcon = {
                IconButton(onClick = onBackPressed) {
                    Image(
                        imageVector = Icons.Default.ArrowBack,
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
                        contentDescription = stringResource(R.string.back),
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        )

        when (artworkState) {
            is UiState.Success -> ArtworkDetailContent(artwork = artworkState.data)
            is UiState.Error -> ErrorScreen(
                message = artworkState.message,
                onRetry = onRetry
            )

            is UiState.Loading -> LoadingUI()
        }
    }
}

@Composable
fun LoadingUI() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}


@Composable
fun ArtworkDetailContent(artwork: ArtworkDetailsUiModel) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        artwork.imageUrl?.let { url ->
            AsyncImage(
                model = url,
                contentDescription = artwork.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        Text(text = artwork.title, style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.artist, artwork.artist),
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = artwork.description, style = MaterialTheme.typography.bodyMedium)
    }
}