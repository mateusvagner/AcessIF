package com.mv.acessif.presentation.root.demoTranscription

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mv.acessif.domain.repository.TranscriptionRepository
import com.mv.acessif.domain.returnModel.DataError
import com.mv.acessif.domain.returnModel.Result
import com.mv.acessif.presentation.asErrorUiText
import com.mv.acessif.presentation.asUiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class DemoTranscriptionViewModel(
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

    var state = mutableStateOf(DemoTranscriptionScreenState())
        private set

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
                isLoading = true,
                error = null,
            )

        viewModelScope.launch(dispatcher) {
            val transcriptionResult =
                transcriptionRepository.transcribeDemo(file)
            when (transcriptionResult) {
                is Result.Success -> {
                    state.value =
                        state.value.copy(
                            isLoading = false,
                            transcription = transcriptionResult.data.text,
                        )
                }

                is Result.Error -> {
                    state.value =
                        state.value.copy(
                            isLoading = false,
                            error = transcriptionResult.asErrorUiText(),
                        )
                }
            }
        }
    }

    fun handleFileUriError() {
        state.value =
            state.value.copy(
                isLoading = false,
                error = DataError.Local.FILE_NOT_FOUND.asUiText(),
            )
    }
}
