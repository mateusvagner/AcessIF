package com.mv.acessif.presentation.home.home

import android.content.Context
import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.mv.acessif.R
import com.mv.acessif.domain.repository.SharedPreferencesRepository
import com.mv.acessif.domain.repository.TranscriptionRepository
import com.mv.acessif.domain.repository.UserRepository
import com.mv.acessif.domain.returnModel.DataError
import com.mv.acessif.domain.returnModel.Result
import com.mv.acessif.presentation.asErrorUiText
import com.mv.acessif.presentation.asUiText
import com.mv.acessif.presentation.navigation.Navigator
import com.mv.acessif.presentation.root.RootGraph
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
    @Inject
    constructor(
        savedStateHandle: SavedStateHandle,
        private val sharedPreferencesRepository: SharedPreferencesRepository,
        private val userRepository: UserRepository,
        private val transcriptionRepository: TranscriptionRepository,
        navigator: Navigator,
    ) : ViewModel(), Navigator by navigator {
        private val _state =
            MutableStateFlow(
                HomeScreenState(userName = savedStateHandle.toRoute<HomeGraph.HomeRoute>().userName),
            )

        val state =
            _state
                .onStart { getLastTranscriptions() }
                .stateIn(
                    viewModelScope,
                    SharingStarted.WhileSubscribed(5000L),
                    HomeScreenState(),
                )

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

                when (val transcriptionsResult = transcriptionRepository.getLastTranscriptions()) {
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
                when (val transcriptionResult = transcriptionRepository.transcribeId(file)) {
                    is Result.Success -> {
                        _state.value =
                            _state.value.copy(
                                isLoadingTranscription = false,
                                errorTranscription = null,
                            )

                        navigateTo(
                            HomeGraph.TranscriptionDetailRoute(
                                transcriptionId = transcriptionResult.data,
                                originScreen = R.string.home_screen,
                            ),
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

        fun handleIntent(intent: HomeIntent) {
            viewModelScope.launch {
                when (intent) {
                    HomeIntent.OnLogout -> {
                        sharedPreferencesRepository.clearTokens()
                        userRepository.logout()

                        navigateTo(RootGraph.WelcomeRoute) {
                            popUpTo<RootGraph.HomeGraph> {
                                inclusive = true
                            }
                        }
                    }

                    HomeIntent.OnMyTranscriptions -> {
                        viewModelScope.launch {
                            navigateTo(HomeGraph.TranscriptionsRoute)
                        }
                    }

                    HomeIntent.OnNewTranscription -> {
                        // TODO
                    }

                    is HomeIntent.OnTranscriptionPressed -> {
                        navigateTo(
                            HomeGraph.TranscriptionDetailRoute(
                                transcriptionId = intent.transcription.id,
                                originScreen = R.string.home_screen,
                            ),
                        )
                    }

                    HomeIntent.OnReloadScreen -> {
                        _state.value =
                            _state.value.copy(
                                isLoadingTranscription = false,
                                errorTranscription = null,
                            )

                        getLastTranscriptions()
                    }

                    HomeIntent.OnAboutTheProject -> {
                        // TODO()
                    }

                    HomeIntent.OnContactUs -> {
                        // TODO()
                    }
                }
            }
        }
    }
