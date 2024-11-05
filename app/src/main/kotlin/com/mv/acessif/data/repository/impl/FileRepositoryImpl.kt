package com.mv.acessif.data.repository.impl

import android.net.Uri
import com.mv.acessif.data.repository.FileRepository
import com.mv.acessif.local.FileReader
import java.io.File
import javax.inject.Inject

class FileRepositoryImpl
    @Inject
    constructor(
        private val fileReader: FileReader,
    ) : FileRepository {
        override suspend fun getFileFromUri(uri: Uri): File {
            return fileReader.getFileFromUri(uri)
        }
    }
