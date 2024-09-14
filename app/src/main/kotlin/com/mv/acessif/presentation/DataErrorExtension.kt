package com.mv.acessif.presentation

import com.mv.acessif.R
import com.mv.acessif.domain.returnModel.DataError
import com.mv.acessif.domain.returnModel.Result

fun DataError.asUiText(): UiText {
    return when (this) {
        is DataError.Network -> {
            when (this) {
                DataError.Network.NO_INTERNET -> UiText.StringResource(R.string.no_internet)
                DataError.Network.TIMEOUT -> UiText.StringResource(R.string.timeout)
                DataError.Network.BAD_REQUEST -> UiText.StringResource(R.string.bad_request)
                DataError.Network.UNAUTHORIZED -> UiText.StringResource(R.string.unauthorized)
                DataError.Network.NOT_FOUND -> UiText.StringResource(R.string.not_found)
                DataError.Network.TOKEN_EXPIRED -> UiText.StringResource(R.string.token_expired)
                DataError.Network.INTERNAL_SERVER_ERROR -> UiText.StringResource(R.string.internal_server_error)
                DataError.Network.SERVER_UNAVAILABLE -> UiText.StringResource(R.string.server_unavailable)
                DataError.Network.SERIALIZATION_ERROR -> UiText.StringResource(R.string.serialization_error)
                DataError.Network.UNKNOWN -> UiText.StringResource(R.string.unknown_error)
            }
        }
        is DataError.Local -> {
            when (this) {
                DataError.Local.PERMISSION_DENIED -> UiText.StringResource(R.string.permission_denied)
                DataError.Local.FILE_NOT_FOUND -> UiText.StringResource(R.string.file_not_found)
            }
        }
    }
}

fun Result.Error<*, DataError>.asErrorUiText(): UiText {
    return error.asUiText()
}
