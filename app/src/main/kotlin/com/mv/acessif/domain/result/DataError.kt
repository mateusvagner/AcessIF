package com.mv.acessif.domain.result

sealed interface DataError : Error {
    enum class Network : DataError {
        NO_INTERNET,
        TIMEOUT, // 408
        BAD_REQUEST, // 400
        UNAUTHORIZED, // 401
        NOT_FOUND, // 404
        TOKEN_EXPIRED, // 403
        INTERNAL_SERVER_ERROR, // 500
        SERVER_UNAVAILABLE, // 503
        SERIALIZATION_ERROR,
        MOVED_PERMANENTLY, // 301
        UNKNOWN,
    }

    enum class Local : DataError {
        PERMISSION_DENIED,
        FILE_NOT_FOUND,
        NULL_POINTER,
        EMPTY_RESULT,
        UNKNOWN,
    }
}
