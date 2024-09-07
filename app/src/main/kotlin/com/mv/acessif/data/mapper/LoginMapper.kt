package com.mv.acessif.data.mapper

import com.mv.acessif.domain.Login
import com.mv.acessif.network.dto.LoginRequestDto

object LoginMapper {
    fun mapLoginToLoginRequestDto(login: Login): LoginRequestDto {
        return LoginRequestDto(
            email = login.email,
            password = login.password,
        )
    }
}
