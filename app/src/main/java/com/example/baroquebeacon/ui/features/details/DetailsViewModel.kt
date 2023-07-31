package com.example.baroquebeacon.ui.features.details

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baroquebeacon.R
import com.example.baroquebeacon.di.CoroutineModule.DISPATCHER_IO
import com.example.baroquebeacon.domain.FetchArtworkDetailUseCase
import com.example.baroquebeacon.ui.features.common.UiState
import com.example.baroquebeacon.ui.features.details.model.ArtworkDetailsUiModel
import com.example.baroquebeacon.ui.features.details.model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val artworkDetailUseCase: FetchArtworkDetailUseCase,
    @Named(DISPATCHER_IO) private val ioDispatcher: CoroutineDispatcher,
    @ApplicationContext private val appContext: Context,
    private val stateHandle: SavedStateHandle
) : ViewModel() {

    private val artworkId: String? = stateHandle["artworkId"]

    // Mutable state for art data
    private val _artworkDetailState =
        MutableStateFlow<UiState<ArtworkDetailsUiModel>>(UiState.Loading)
    val artworkDetailState: StateFlow<UiState<ArtworkDetailsUiModel>> =
        _artworkDetailState.asStateFlow()

    init {
        fetchDetails()
    }

    // Fetch artworks and update the state
    fun fetchDetails() {
        artworkId?.let { id ->
            viewModelScope.launch(ioDispatcher) {
                _artworkDetailState.value = UiState.Loading
                artworkDetailUseCase(id).let { result ->
                    when {
                        result.isSuccess -> {
                            _artworkDetailState.value =
                                UiState.Success(result.getOrNull()!!.toUiModel())
                        }

                        result.isFailure -> {
                            _artworkDetailState.value =
                                UiState.Error(
                                    result.exceptionOrNull()?.message ?: appContext.getString(
                                        R.string.unknown_error
                                    )
                                )
                        }
                    }
                }
            }
        } ?: run {
            _artworkDetailState.value =
                UiState.Error(appContext.getString(R.string.no_artwork_id_provided))
        }
    }
}