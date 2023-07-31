package com.example.baroquebeacon.ui.features.overview

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baroquebeacon.R
import com.example.baroquebeacon.di.CoroutineModule.DISPATCHER_IO
import com.example.baroquebeacon.domain.FetchArtworksUseCase
import com.example.baroquebeacon.domain.model.Artwork
import com.example.baroquebeacon.ui.features.common.UiState
import com.example.baroquebeacon.ui.features.overview.model.OverviewFeedUiItem
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class OverviewViewModel @Inject constructor(
    private val fetchArtworkPageUseCase: FetchArtworksUseCase,
    private val artworkPageMerger: ArtworkPageMerger,
    @ApplicationContext private val appContext: Context,
    @Named(DISPATCHER_IO) private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {

    // Mutable state for the current page
    private var currentPage: Int = 0

    // Mutable state for art data
    private val _artworks = MutableStateFlow<UiState<List<OverviewFeedUiItem>>>(UiState.Loading)
    val artworks: StateFlow<UiState<List<OverviewFeedUiItem>>> = _artworks.asStateFlow()

    // Mutex to prevent concurrent fetches
    private val mutex = Mutex()

    init {
        fetchArtworks()
    }

    fun reload() {
        currentPage = 0
        fetchArtworks()
    }

    fun fetchArtworks() {
        viewModelScope.launch(ioDispatcher) {
            if (mutex.isLocked) return@launch

            mutex.withLock {
                notifyLoadingState()

                fetchArtworkPageUseCase(currentPage).let { result ->
                    when {
                        result.isSuccess -> {
                            onFetchArtworksSuccess(result)
                        }

                        result.isFailure -> onFetchArtworksFailure(result)
                    }
                }
            }
        }
    }

    private fun notifyLoadingState() {
        if (currentPage == 0) {
            _artworks.value = UiState.Loading
        } else {
            val newData = getCurrentData() + OverviewFeedUiItem.LoadingIndicator
            _artworks.value = UiState.Success(newData)
        }
    }

    private fun onFetchArtworksSuccess(result: Result<Map<String, List<Artwork>>>) {
        val newData = result.getOrNull().orEmpty()
        val currentData = getCurrentData()

        val mergedData = artworkPageMerger.merge(
            currentData = currentData,
            newData = newData,
        )

        _artworks.value = UiState.Success(mergedData)
        currentPage++  // Increment the current page after successful fetch
    }

    private fun onFetchArtworksFailure(result: Result<Map<String, List<Artwork>>>) {
        _artworks.value = UiState.Error(
            result.exceptionOrNull()?.message ?: appContext.getString(R.string.unknown_error)
        )
    }

    private fun getCurrentData() = if (_artworks.value is UiState.Success) {
        (_artworks.value as UiState.Success).data
    } else {
        emptyList()
    }
}