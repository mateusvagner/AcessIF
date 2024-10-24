package com.mv.acessif.util

import android.content.Context
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
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
            val inputStream = context.contentResolver.openInputStream(uri)
            val file = File(context.cacheDir, "selected_audio_file")
            inputStream?.use { input ->
                file.outputStream().use { output ->
                    input.copyTo(output)
                }
            }

            return file
        }
    }
