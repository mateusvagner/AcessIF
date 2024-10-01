package com.mv.acessif.data.mapper

import com.mv.acessif.domain.AuthToken
import com.mv.acessif.network.dto.AuthTokenDto

object AuthTokenMapper {
    fun mapAuthTokenDtoToAuthToken(authTokenDto: AuthTokenDto): AuthToken {
        return AuthToken(
            accessToken = authTokenDto.accessToken,
            refreshToken = authTokenDto.refreshToken,
        )
    }
}
