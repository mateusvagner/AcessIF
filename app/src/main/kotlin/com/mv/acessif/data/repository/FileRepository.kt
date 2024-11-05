package com.mv.acessif.data.repository

import android.net.Uri
import java.io.File

interface FileRepository {
    suspend fun getFileFromUri(uri: Uri): File
}
