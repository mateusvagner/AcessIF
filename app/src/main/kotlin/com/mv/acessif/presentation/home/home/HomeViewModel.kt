package com.mv.acessif.presentation.home.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mv.acessif.domain.repository.SharedPreferencesRepository
import com.mv.acessif.domain.repository.TranscriptionRepository
import com.mv.acessif.domain.returnModel.Result
import com.mv.acessif.presentation.asUiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel(
    private val sharedPreferencesRepository: SharedPreferencesRepository,
    private val transcriptionRepository: TranscriptionRepository,
    private val dispatcher: CoroutineDispatcher,
) : ViewModel() {
    @Inject
    constructor(
        sharedPreferencesRepository: SharedPreferencesRepository,
        transcriptionRepository: TranscriptionRepository,
    ) : this(
        sharedPreferencesRepository = sharedPreferencesRepository,
        transcriptionRepository = transcriptionRepository,
        dispatcher = Dispatchers.IO,
    )

    var state = mutableStateOf(HomeScreenState())
        private set

    private val _onLogoutSuccess = Channel<Unit>()
    val onLogoutSuccess =
        _onLogoutSuccess.receiveAsFlow().shareIn(viewModelScope, SharingStarted.Lazily)

    init {
        getLastTranscriptions()
    }

    private fun getLastTranscriptions() {
        viewModelScope.launch(dispatcher) {
            state.value =
                state.value.copy(
                    isLoadingTranscriptions = true,
                    transcriptionsError = null,
                    transcriptions = emptyList(),
                )

            when (val transcriptionsResult = transcriptionRepository.getLastTranscriptions()) {
                is Result.Success -> {
                    state.value =
                        state.value.copy(
                            isLoadingTranscriptions = false,
                            transcriptionsError = null,
                            transcriptions = transcriptionsResult.data,
                        )
                }

                is Result.Error -> {
                    state.value =
                        state.value.copy(
                            isLoadingTranscriptions = false,
                            transcriptionsError = transcriptionsResult.error.asUiText(),
                            transcriptions = emptyList(),
                        )
                }
            }
        }
    }

    fun logoutUser() {
        sharedPreferencesRepository.clearTokens()

        viewModelScope.launch {
            _onLogoutSuccess.send(Unit)
        }
    }
}
