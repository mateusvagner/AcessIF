package com.mv.acessif.presentation.home.transcriptions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mv.acessif.R
import com.mv.acessif.domain.repository.TranscriptionRepository
import com.mv.acessif.domain.returnModel.Result
import com.mv.acessif.presentation.asUiText
import com.mv.acessif.presentation.home.home.HomeGraph
import com.mv.acessif.presentation.navigation.Navigator
import com.mv.acessif.presentation.util.groupByFormattedDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TranscriptionsViewModel
    @Inject
    constructor(
        private val transcriptionRepository: TranscriptionRepository,
        private val navigator: Navigator,
    ) : ViewModel(), Navigator by navigator {
        private val _state = MutableStateFlow(TranscriptionsScreenState())
        val state =
            _state
                .onStart { getTranscriptions() }
                .stateIn(
                    viewModelScope,
                    SharingStarted.WhileSubscribed(5000L),
                    TranscriptionsScreenState(),
                )

        fun getTranscriptions() {
            viewModelScope.launch {
                _state.value =
                    _state.value.copy(
                        isLoading = true,
                        error = null,
                        transcriptions = emptyMap(),
                        favoriteTranscriptions = emptyList(),
                        transcriptionUpdateType = null,
                    )

                when (val transcriptionsResult = transcriptionRepository.getTranscriptions()) {
                    is Result.Success -> {
                        _state.value =
                            _state.value.copy(
                                isLoading = false,
                                error = null,
                                transcriptions = transcriptionsResult.data.groupByFormattedDate(),
                                favoriteTranscriptions = transcriptionsResult.data.filter { it.isFavorite },
                                transcriptionUpdateType = null,
                            )
                    }

                    is Result.Error -> {
                        _state.value =
                            _state.value.copy(
                                isLoading = false,
                                error = transcriptionsResult.error.asUiText(),
                                transcriptions = emptyMap(),
                                favoriteTranscriptions = emptyList(),
                                transcriptionUpdateType = null,
                            )
                    }
                }
            }
        }

        fun deleteTranscription(id: Int) {
            viewModelScope.launch {
                _state.value =
                    _state.value.copy(
                        transcriptionUpdateType = TranscriptionUpdateType.DELETE,
                    )

                when (val result = transcriptionRepository.deleteTranscription(id)) {
                    is Result.Success -> {
                        getTranscriptions()
                    }

                    is Result.Error -> {
                        _state.value =
                            _state.value.copy(
                                isLoading = false,
                                error = result.error.asUiText(),
                                transcriptions = emptyMap(),
                                favoriteTranscriptions = emptyList(),
                                transcriptionUpdateType = null,
                            )
                    }
                }
            }
        }

        fun favoriteTranscription(id: Int) {
            viewModelScope.launch {
                _state.value =
                    _state.value.copy(
                        transcriptionUpdateType = TranscriptionUpdateType.FAVORITE,
                    )

                when (val result = transcriptionRepository.favoriteTranscription(id)) {
                    is Result.Success -> {
                        getTranscriptions()
                    }

                    is Result.Error -> {
                        _state.value =
                            _state.value.copy(
                                isLoading = false,
                                error = result.error.asUiText(),
                                transcriptions = emptyMap(),
                                favoriteTranscriptions = emptyList(),
                                transcriptionUpdateType = null,
                            )
                    }
                }
            }
        }

        fun handleIntent(intent: TranscriptionsIntent) {
            viewModelScope.launch {
                when (intent) {
                    TranscriptionsIntent.OnNavigateBack -> {
                        navigator.navigateUp()
                    }

                    TranscriptionsIntent.OnTryAgain -> {
                        getTranscriptions()
                    }

                    TranscriptionsIntent.OnNewTranscription -> {
                        // TODO -> Add logic
                    }

                    is TranscriptionsIntent.OnOpenTranscriptionDetail -> {
                        navigator.navigateTo(
                            HomeGraph.TranscriptionDetailRoute(
                                transcriptionId = intent.transcriptionId,
                                originScreen = R.string.my_transcriptions_screen,
                            ),
                        )
                    }

                    is TranscriptionsIntent.OnDeleteTranscription -> {
                        deleteTranscription(intent.transcriptionId)
                    }

                    is TranscriptionsIntent.OnFavoriteTranscription -> {
                        favoriteTranscription(intent.transcriptionId)
                    }
                }
            }
        }
    }
