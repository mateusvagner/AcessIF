package com.mv.acessif.data.repository

import android.net.Uri
import com.mv.acessif.domain.result.DataError
import com.mv.acessif.domain.result.Result
import java.io.File

interface FileRepository {
    suspend fun getFileFromUri(uri: Uri): Result<File, DataError.Local>
}
