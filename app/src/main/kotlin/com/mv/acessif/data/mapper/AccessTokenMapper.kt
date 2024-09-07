package com.mv.acessif.data.mapper

import com.mv.acessif.domain.AccessToken
import com.mv.acessif.network.dto.AccessTokenDto

object AccessTokenMapper {
    fun mapAccessTokenDtoToAccessToken(accessTokenDto: AccessTokenDto): AccessToken {
        return AccessToken(
            accessToken = accessTokenDto.accessToken,
        )
    }
}
