package com.mv.acessif.presentation.home.transcriptions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mv.acessif.domain.repository.TranscriptionRepository
import com.mv.acessif.domain.returnModel.Result
import com.mv.acessif.presentation.asUiText
import com.mv.acessif.presentation.home.home.HomeScreenState
import com.mv.acessif.presentation.util.groupByFormattedDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TranscriptionsViewModel(
    private val transcriptionRepository: TranscriptionRepository,
    private val dispatcher: CoroutineDispatcher,
) : ViewModel() {
    @Inject
    constructor(
        transcriptionRepository: TranscriptionRepository,
    ) : this(
        transcriptionRepository = transcriptionRepository,
        dispatcher = Dispatchers.IO,
    )

    private val _state = MutableStateFlow(TranscriptionsScreenState())
    val state = _state
        .onStart { getTranscriptions() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(3000L),
            TranscriptionsScreenState(),
        )

    fun getTranscriptions() {
        viewModelScope.launch {
            _state.value =
                _state.value.copy(
                    isLoading = true,
                    error = null,
                    transcriptions = emptyMap(),
                    isDeletingTranscription = false,
                )

            val transcriptionsResult =
                withContext(dispatcher) {
                    transcriptionRepository.getTranscriptions()
                }

            when (transcriptionsResult) {
                is Result.Success -> {
                    _state.value =
                        _state.value.copy(
                            isLoading = false,
                            error = null,
                            transcriptions = transcriptionsResult.data.groupByFormattedDate(),
                            isDeletingTranscription = false,
                        )
                }

                is Result.Error -> {
                    _state.value =
                        _state.value.copy(
                            isLoading = false,
                            error = transcriptionsResult.error.asUiText(),
                            transcriptions = emptyMap(),
                            isDeletingTranscription = false,
                        )
                }
            }
        }
    }

    fun deleteTranscription(id: Int) {
        viewModelScope.launch {
            _state.value =
                _state.value.copy(
                    isDeletingTranscription = true,
                )

            val result =
                withContext(dispatcher) {
                    transcriptionRepository.deleteTranscription(id)
                }

            when (result) {
                is Result.Success -> {
                    getTranscriptions()
                }

                is Result.Error -> {
                    _state.value =
                        _state.value.copy(
                            isLoading = false,
                            error = result.error.asUiText(),
                            transcriptions = emptyMap(),
                            isDeletingTranscription = false,
                        )
                }
            }
        }
    }
}
