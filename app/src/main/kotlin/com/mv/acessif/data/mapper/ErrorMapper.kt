package com.mv.acessif.data.mapper

import com.mv.acessif.domain.returnModel.DataError
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.SerializationException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object ErrorMapper {
    fun mapNetworkExceptionToNetworkDataError(exception: Exception): DataError.Network {
        return when (exception) {
            is ClientRequestException -> {
                when (exception.response.status) {
                    HttpStatusCode.BadRequest -> DataError.Network.BAD_REQUEST
                    HttpStatusCode.Unauthorized -> DataError.Network.UNAUTHORIZED
                    HttpStatusCode.Forbidden -> DataError.Network.TOKEN_EXPIRED
                    HttpStatusCode.NotFound -> DataError.Network.NOT_FOUND
                    else -> DataError.Network.UNKNOWN
                }
            }

            is ServerResponseException -> {
                when (exception.response.status) {
                    HttpStatusCode.InternalServerError -> DataError.Network.INTERNAL_SERVER_ERROR
                    HttpStatusCode.ServiceUnavailable -> DataError.Network.SERVER_UNAVAILABLE
                    else -> DataError.Network.UNKNOWN
                }
            }

            is ResponseException -> {
                when (exception.response.status) {
                    HttpStatusCode.RequestTimeout -> DataError.Network.TIMEOUT
                    else -> DataError.Network.UNKNOWN
                }
            }

            is SerializationException -> DataError.Network.SERIALIZATION_ERROR

            is ConnectException -> DataError.Network.NO_INTERNET

            is UnknownHostException -> DataError.Network.NO_INTERNET

            is SocketTimeoutException -> DataError.Network.TIMEOUT // TODO Review this

            else -> DataError.Network.UNKNOWN
        }
    }

    fun mapExceptionToDataError(exception: Exception): DataError {
        return when (exception) {
            is ClientRequestException -> {
                when (exception.response.status) {
                    HttpStatusCode.BadRequest -> DataError.Network.BAD_REQUEST
                    HttpStatusCode.Unauthorized -> DataError.Network.UNAUTHORIZED
                    HttpStatusCode.Forbidden -> DataError.Network.TOKEN_EXPIRED
                    HttpStatusCode.NotFound -> DataError.Network.NOT_FOUND
                    else -> DataError.Network.UNKNOWN
                }
            }

            is ServerResponseException -> {
                when (exception.response.status) {
                    HttpStatusCode.InternalServerError -> DataError.Network.INTERNAL_SERVER_ERROR
                    HttpStatusCode.ServiceUnavailable -> DataError.Network.SERVER_UNAVAILABLE
                    else -> DataError.Network.UNKNOWN
                }
            }

            is ResponseException -> {
                when (exception.response.status) {
                    HttpStatusCode.RequestTimeout -> DataError.Network.TIMEOUT
                    else -> DataError.Network.UNKNOWN
                }
            }

            is SerializationException -> DataError.Network.SERIALIZATION_ERROR

            is ConnectException -> DataError.Network.NO_INTERNET

            is UnknownHostException -> DataError.Network.NO_INTERNET

            is SocketTimeoutException -> DataError.Network.TIMEOUT // TODO Review this

            else -> DataError.Network.UNKNOWN

            // TODO local errors
        }
    }
}
