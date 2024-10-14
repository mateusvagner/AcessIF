package com.mv.acessif.presentation.home.home

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mv.acessif.domain.repository.SharedPreferencesRepository
import com.mv.acessif.domain.repository.TranscriptionRepository
import com.mv.acessif.domain.returnModel.DataError
import com.mv.acessif.domain.returnModel.Result
import com.mv.acessif.presentation.asErrorUiText
import com.mv.acessif.presentation.asUiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
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

    sealed interface HomeEvent {
        data object OnLogout : HomeEvent

        data class OnTranscriptionDone(val id: Int) : HomeEvent
    }

    private val _state = MutableStateFlow(HomeScreenState())
    val state =
        _state
            .onStart { getLastTranscriptions() }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(3000L),
                HomeScreenState(),
            )

    private val _onEventSuccess = Channel<HomeEvent>()
    val onEventSuccess =
        _onEventSuccess.receiveAsFlow().shareIn(viewModelScope, SharingStarted.Lazily)

    private fun getLastTranscriptions() {
        viewModelScope.launch {
            _state.value =
                _state.value.copy(
                    transcriptionsSectionState =
                        state.value.transcriptionsSectionState.copy(
                            isLoading = true,
                            error = null,
                            transcriptions = emptyList(),
                        ),
                )

            val transcriptionsResult =
                withContext(dispatcher) {
                    transcriptionRepository.getLastTranscriptions()
                }

            when (transcriptionsResult) {
                is Result.Success -> {
                    _state.value =
                        _state.value.copy(
                            transcriptionsSectionState =
                                state.value.transcriptionsSectionState.copy(
                                    isLoading = false,
                                    error = null,
                                    transcriptions = transcriptionsResult.data,
                                ),
                        )
                }

                is Result.Error -> {
                    _state.value =
                        _state.value.copy(
                            transcriptionsSectionState =
                                state.value.transcriptionsSectionState.copy(
                                    isLoading = false,
                                    error = transcriptionsResult.error.asUiText(),
                                    transcriptions = emptyList(),
                                ),
                        )
                }
            }
        }
    }

    fun handleFileUri(
        uri: Uri,
        context: Context,
    ) {
        val inputStream = context.contentResolver.openInputStream(uri)
        val file = File(context.cacheDir, "selected_audio_file")
        inputStream?.use { input ->
            file.outputStream().use { output ->
                input.copyTo(output)
            }
        }
        transcribeFile(file)
    }

    private fun transcribeFile(file: File) {
        _state.value =
            _state.value.copy(
                isLoadingTranscription = true,
                errorTranscription = null,
            )

        viewModelScope.launch {
            val transcriptionResult =
                withContext(dispatcher) {
                    transcriptionRepository.transcribeId(file)
                }

            when (transcriptionResult) {
                is Result.Success -> {
                    _state.value =
                        _state.value.copy(
                            isLoadingTranscription = false,
                            errorTranscription = null,
                        )

                    _onEventSuccess.send(
                        HomeEvent.OnTranscriptionDone(transcriptionResult.data),
                    )
                }

                is Result.Error -> {
                    _state.value =
                        _state.value.copy(
                            isLoadingTranscription = false,
                            errorTranscription = transcriptionResult.asErrorUiText(),
                        )
                }
            }
        }
    }

    fun handleFileUriError() {
        _state.value =
            _state.value.copy(
                isLoadingTranscription = false,
                errorTranscription = DataError.Local.FILE_NOT_FOUND.asUiText(),
            )
    }

    fun logoutUser() {
        sharedPreferencesRepository.clearTokens()

        viewModelScope.launch {
            _onEventSuccess.send(HomeEvent.OnLogout)
        }
    }
}
