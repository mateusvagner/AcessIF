package com.mv.acessif.data.repository.impl

import android.net.Uri
import com.mv.acessif.data.mapper.ErrorMapper
import com.mv.acessif.data.repository.FileRepository
import com.mv.acessif.domain.result.DataError
import com.mv.acessif.domain.result.Result
import com.mv.acessif.local.FileReader
import java.io.File
import javax.inject.Inject

class FileRepositoryImpl
    @Inject
    constructor(
        private val fileReader: FileReader,
    ) : FileRepository {
        override suspend fun getFileFromUri(uri: Uri): Result<File, DataError.Local> {
            return try {
                Result.Success(fileReader.getFileFromUri(uri))
            } catch (e: Exception) {
                Result.Error(ErrorMapper.mapLocalExceptionToLocalDataError(e))
            }
        }
    }
