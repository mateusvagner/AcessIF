package com.mv.acessif.presentation.home.transcriptions

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mv.acessif.domain.repository.TranscriptionRepository
import com.mv.acessif.domain.returnModel.Result
import com.mv.acessif.presentation.asUiText
import com.mv.acessif.presentation.util.groupByFormattedDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

    var state = mutableStateOf(TranscriptionsScreenState())
        private set

    init {
        getTranscriptions()
    }

    fun getTranscriptions() {
        viewModelScope.launch(dispatcher) {
            state.value =
                state.value.copy(
                    isLoading = true,
                    error = null,
                    transcriptions = emptyMap(),
                )

            when (val transcriptionsResult = transcriptionRepository.getTranscriptions()) {
                is Result.Success -> {
                    state.value =
                        state.value.copy(
                            isLoading = false,
                            error = null,
                            transcriptions = transcriptionsResult.data.groupByFormattedDate(),
                        )
                }

                is Result.Error -> {
                    state.value =
                        state.value.copy(
                            isLoading = false,
                            error = transcriptionsResult.error.asUiText(),
                            transcriptions = emptyMap(),
                        )
                }
            }
        }
    }
}
