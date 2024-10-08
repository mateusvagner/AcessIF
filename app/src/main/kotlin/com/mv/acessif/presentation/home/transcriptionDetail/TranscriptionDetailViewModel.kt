package com.mv.acessif.presentation.home.transcriptionDetail

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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

    private val _state = MutableStateFlow(TranscriptionDetailScreenState())
    val state = _state.asStateFlow()

    init {
        getTranscriptionDetail()
    }

    override fun onCleared() {
        super.onCleared()
        player.release()
    }

    private fun getTranscriptionDetail() {
        viewModelScope.launch {
            _state.value =
                _state.value.copy(
                    isLoading = true,
                    error = null,
                    transcription = null,
                )

            val transcriptionDetailResult =
                withContext(dispatcher) {
                    transcriptionRepository.getTranscriptionById(transcriptionId)
                }

            when (transcriptionDetailResult) {
                is Result.Success -> {
                    _state.value =
                        _state.value.copy(
                            isLoading = false,
                            transcription = transcriptionDetailResult.data,
                        )
                    val audioUrl =
                        transcriptionRepository.getAudioUrl(
                            transcriptionDetailResult.data.audioId.orEmpty(),
                        )

                    setupPlayer(audioUrl)
                }

                is Result.Error -> {
                    _state.value =
                        _state.value.copy(
                            isLoading = false,
                            error = transcriptionDetailResult.error.asUiText(),
                        )
                }
            }
        }
    }

    private fun setupPlayer(audioUrl: String) {
        if (audioUrl.isEmpty()) {
            return
        }

        val mediaItem = MediaItem.fromUri(audioUrl)

        player.setMediaItem(mediaItem)
        player.prepare()

        startTrackingCurrentPosition()
    }

    private fun startTrackingCurrentPosition() {
        viewModelScope.launch {
            while (true) {
                _state.value =
                    _state.value.copy(
                        currentPosition = player.currentPosition.toFloat() / 1000,
                    )

                delay(500)
            }
        }
    }

    fun onTryAgainClicked() {
        getTranscriptionDetail()
    }

    fun onNewTranscriptionName(newName: String) {
        viewModelScope.launch {
            val transcriptionId = _state.value.transcription?.id ?: return@launch

            val result =
                withContext(dispatcher) {
                    transcriptionRepository.updateTranscriptionName(
                        id = transcriptionId,
                        name = newName,
                    )
                }

            when (result) {
                is Result.Success -> {
                    getTranscriptionDetail()
                }

                is Result.Error -> {
                    _state.value =
                        _state.value.copy(
                            isLoading = false,
                            error = result.error.asUiText(),
                        )
                }
            }
        }
    }
}
