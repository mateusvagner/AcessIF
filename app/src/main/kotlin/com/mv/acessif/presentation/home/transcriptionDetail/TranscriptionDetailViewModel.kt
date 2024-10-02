package com.mv.acessif.presentation.home.transcriptionDetail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.navigation.toRoute
import com.mv.acessif.domain.repository.TranscriptionRepository
import com.mv.acessif.domain.returnModel.Result
import com.mv.acessif.presentation.asUiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TranscriptionDetailViewModel(
    private val transcriptionId: Int,
    private val transcriptionRepository: TranscriptionRepository,
    private val dispatcher: CoroutineDispatcher,
    val player: Player,
) : ViewModel() {
    @Inject
    constructor(
        transcriptionRepository: TranscriptionRepository,
        savedStateHandle: SavedStateHandle,
        player: Player,
    ) : this(
        transcriptionId = savedStateHandle.toRoute<TranscriptionDetailScreen>().transcriptionId,
        transcriptionRepository = transcriptionRepository,
        dispatcher = Dispatchers.IO,
        player = player,
    )

    var state = mutableStateOf(TranscriptionDetailScreenState())
        private set

    init {
        getTranscriptionDetail()
        player.prepare()
    }

    override fun onCleared() {
        super.onCleared()
        player.release()
    }

    private fun getTranscriptionDetail() {
        viewModelScope.launch(dispatcher) {
            state.value =
                state.value.copy(
                    isLoading = true,
                    error = null,
                    transcription = null,
                )

            when (
                val transcriptionDetailResult =
                    transcriptionRepository.getTranscriptionById(transcriptionId)
            ) {
                is Result.Success -> {
                    state.value =
                        state.value.copy(
                            isLoading = false,
                            transcription = transcriptionDetailResult.data,
                        )
                    setupMediaItem()
                }

                is Result.Error -> {
                    state.value =
                        state.value.copy(
                            isLoading = false,
                            error = transcriptionDetailResult.error.asUiText(),
                        )
                }
            }
        }
    }

    private fun setupMediaItem() {
        val mediaItem = state.value.transcription?.audioId?.let { MediaItem.fromUri(it) }
        if (mediaItem != null) {
            player.setMediaItem(mediaItem)
        }
    }

    fun onTryAgainClicked() {
        getTranscriptionDetail()
    }
}
