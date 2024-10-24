package com.mv.acessif.util

import android.content.Context
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

interface FileReader {
    suspend fun getFileFromUri(uri: Uri): File
}

class DefaultFileReader
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
        private val dispatcherProvider: DispatcherProvider,
    ) : FileReader {
        override suspend fun getFileFromUri(uri: Uri): File {
            return withContext(dispatcherProvider.io) {
                val file = File(context.cacheDir, "selected_audio_file")

                // TODO -> turn it into a cancellable coroutine
                context.contentResolver.openInputStream(uri)?.use { input ->
                    file.outputStream().use { output ->
                        input.copyTo(output)
                    }
                }

                file
            }
        }
    }
