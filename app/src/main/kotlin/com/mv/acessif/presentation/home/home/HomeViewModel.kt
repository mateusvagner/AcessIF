package com.mv.acessif.presentation.home.home

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.mutableStateOf
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
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.shareIn
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

    var state = mutableStateOf(HomeScreenState())
        private set

    private val _onEventSuccess = Channel<HomeEvent>()
    val onEventSuccess =
        _onEventSuccess.receiveAsFlow().shareIn(viewModelScope, SharingStarted.Lazily)

    init {
        getLastTranscriptions()
    }

    private fun getLastTranscriptions() {
        viewModelScope.launch {
            state.value =
                state.value.copy(
                    transcriptionsSectionState =
                    state.value.transcriptionsSectionState.copy(
                        isLoading = true,
                        error = null,
                        transcriptions = emptyList()
                    ),
                )

            when (val transcriptionsResult = transcriptionRepository.getLastTranscriptions()) {
                is Result.Success -> {
                    state.value =
                        state.value.copy(
                            transcriptionsSectionState =
                            state.value.transcriptionsSectionState.copy(
                                isLoading = false,
                                error = null,
                                transcriptions = transcriptionsResult.data,
                            ),
                        )

                }

                is Result.Error -> {
                    state.value =
                        state.value.copy(
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
        state.value =
            state.value.copy(
                isLoadingTranscription = true,
                errorTranscription = null,
            )

        viewModelScope.launch {
            when (val transcriptionResult = transcriptionRepository.transcribeId(file)) {
                is Result.Success -> {
                    state.value =
                        state.value.copy(
                            isLoadingTranscription = false,
                            errorTranscription = null,
                        )

                    withContext(Dispatchers.Main) {
                        _onEventSuccess.send(
                            HomeEvent.OnTranscriptionDone(transcriptionResult.data)
                        )
                    }
                }

                is Result.Error -> {
                    state.value =
                        state.value.copy(
                            isLoadingTranscription = false,
                            errorTranscription = transcriptionResult.asErrorUiText(),
                        )
                }
            }
        }
    }

    fun handleFileUriError() {
        state.value =
            state.value.copy(
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
