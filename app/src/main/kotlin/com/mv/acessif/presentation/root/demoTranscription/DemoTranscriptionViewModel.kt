package com.mv.acessif.presentation.root.demoTranscription

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mv.acessif.domain.repository.TranscriptionRepository
import com.mv.acessif.domain.returnModel.DataError
import com.mv.acessif.domain.returnModel.Result
import com.mv.acessif.presentation.asErrorUiText
import com.mv.acessif.presentation.asUiText
import com.mv.acessif.util.FileReader
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class DemoTranscriptionViewModel
    @Inject
    constructor(
        private val transcriptionRepository: TranscriptionRepository,
        private val fileReader: FileReader,
    ) : ViewModel() {
        private val _state = MutableStateFlow(DemoTranscriptionScreenState())
        val state =
            _state
                .stateIn(
                    viewModelScope,
                    SharingStarted.WhileSubscribed(5000L),
                    DemoTranscriptionScreenState(),
                )

        fun handleFileUri(uri: Uri) {
            viewModelScope.launch {
                val file = fileReader.getFileFromUri(uri)
                transcribeFile(file)
            }
        }

        private fun transcribeFile(file: File) {
            _state.value =
                _state.value.copy(
                    isLoading = true,
                    error = null,
                )

            viewModelScope.launch {
                val transcriptionResult =
                    transcriptionRepository.transcribeDemo(file)
                when (transcriptionResult) {
                    is Result.Success -> {
                        _state.value =
                            _state.value.copy(
                                isLoading = false,
                                transcription = transcriptionResult.data.text,
                            )
                    }

                    is Result.Error -> {
                        _state.value =
                            _state.value.copy(
                                isLoading = false,
                                error = transcriptionResult.asErrorUiText(),
                            )
                    }
                }
            }
        }

        fun handleFileUriError() {
            _state.value =
                _state.value.copy(
                    isLoading = false,
                    error = DataError.Local.FILE_NOT_FOUND.asUiText(),
                )
        }
    }
