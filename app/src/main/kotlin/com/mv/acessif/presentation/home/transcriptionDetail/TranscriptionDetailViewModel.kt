package com.mv.acessif.presentation.home.transcriptionDetail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
) : ViewModel() {
    @Inject
    constructor(
        transcriptionRepository: TranscriptionRepository,
        savedStateHandle: SavedStateHandle,
    ) : this(
        transcriptionId = savedStateHandle.toRoute<TranscriptionDetailScreen>().transcriptionId,
        transcriptionRepository = transcriptionRepository,
        dispatcher = Dispatchers.IO,
    )

    var state = mutableStateOf(TranscriptionDetailScreenState())
        private set

    init {
        getTranscriptionDetail()
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

    fun onTryAgainClicked() {
        getTranscriptionDetail()
    }
}
