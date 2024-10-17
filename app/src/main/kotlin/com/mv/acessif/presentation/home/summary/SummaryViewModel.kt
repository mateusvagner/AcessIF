package com.mv.acessif.presentation.home.summary

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.mv.acessif.domain.repository.SummaryRepository
import com.mv.acessif.domain.returnModel.Result
import com.mv.acessif.presentation.asUiText
import com.mv.acessif.presentation.home.home.HomeGraph
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SummaryViewModel(
    private val transcriptionId: Int,
    private val summaryRepository: SummaryRepository,
    private val dispatcher: CoroutineDispatcher,
) : ViewModel() {
    @Inject
    constructor(
        summaryRepository: SummaryRepository,
        savedStateHandle: SavedStateHandle,
    ) : this(
        transcriptionId = savedStateHandle.toRoute<HomeGraph.SummaryRoute>().transcriptionId,
        summaryRepository = summaryRepository,
        dispatcher = Dispatchers.IO,
    )

    var state = mutableStateOf(SummaryScreenState())
        private set

    init {
        getSummary()
    }

    fun getSummary() {
        viewModelScope.launch {
            state.value =
                state.value.copy(
                    isLoading = true,
                    error = null,
                    summary = null,
                )

            when (
                val summaryResult =
                    summaryRepository.summarizeTranscription(transcriptionId)
            ) {
                is Result.Success -> {
                    state.value =
                        state.value.copy(
                            isLoading = false,
                            summary = summaryResult.data,
                        )
                }

                is Result.Error -> {
                    state.value =
                        state.value.copy(
                            isLoading = false,
                            error = summaryResult.error.asUiText(),
                        )
                }
            }
        }
    }
}
