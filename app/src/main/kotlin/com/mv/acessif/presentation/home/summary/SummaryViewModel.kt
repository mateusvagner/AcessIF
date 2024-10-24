package com.mv.acessif.presentation.home.summary

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.mv.acessif.domain.repository.SummaryRepository
import com.mv.acessif.domain.returnModel.Result
import com.mv.acessif.presentation.asUiText
import com.mv.acessif.presentation.home.home.HomeGraph
import com.mv.acessif.presentation.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SummaryViewModel
    @Inject
    constructor(
        private val summaryRepository: SummaryRepository,
        savedStateHandle: SavedStateHandle,
        navigator: Navigator,
    ) : ViewModel(), Navigator by navigator {
        private val transcriptionId: Int = savedStateHandle.toRoute<HomeGraph.SummaryRoute>().transcriptionId

        private val _state = MutableStateFlow(SummaryScreenState())
        val state =
            _state
                .onStart { getSummary() }
                .stateIn(
                    viewModelScope,
                    SharingStarted.WhileSubscribed(5000L),
                    SummaryScreenState(),
                )

        private fun getSummary() {
            viewModelScope.launch {
                _state.value =
                    _state.value.copy(
                        isLoading = true,
                        error = null,
                        summary = null,
                    )

                when (
                    val summaryResult =
                        summaryRepository.summarizeTranscription(transcriptionId)
                ) {
                    is Result.Success -> {
                        _state.value =
                            _state.value.copy(
                                isLoading = false,
                                summary = summaryResult.data,
                            )
                    }

                    is Result.Error -> {
                        _state.value =
                            _state.value.copy(
                                isLoading = false,
                                error = summaryResult.error.asUiText(),
                            )
                    }
                }
            }
        }

        fun handleIntent(intent: SummaryIntent) {
            viewModelScope.launch {
                when (intent) {
                    SummaryIntent.OnNavigateBack -> {
                        navigateUp()
                    }

                    SummaryIntent.OnTryAgain -> {
                        getSummary()
                    }

                    else -> Unit
                }
            }
        }
    }
