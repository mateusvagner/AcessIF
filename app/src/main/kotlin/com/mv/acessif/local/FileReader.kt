package com.mv.acessif.local

import android.net.Uri
import java.io.File

interface FileReader {
    suspend fun getFileFromUri(uri: Uri): File
}
