package com.mv.acessif.presentation.root.demoTranscription

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mv.acessif.domain.repository.TranscriptionRepository
import com.mv.acessif.domain.returnModel.Result
import com.mv.acessif.presentation.asErrorUiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class DemoTranscriptionViewModel
    @Inject
    constructor(
        private val transcriptionRepository: TranscriptionRepository,
    ) : ViewModel() {
        private val _state = MutableStateFlow(DemoTranscriptionScreenState())
        val state: StateFlow<DemoTranscriptionScreenState> = _state.asStateFlow()

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
                    isLoading = true,
                    error = null,
                )

            viewModelScope.launch {
                val transcriptionResult =
                    transcriptionRepository.postTranscribe(file, isAuthorized = false)
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
    }