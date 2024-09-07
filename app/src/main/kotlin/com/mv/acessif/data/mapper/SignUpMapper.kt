package com.mv.acessif.data.mapper

import com.mv.acessif.domain.SignUp
import com.mv.acessif.network.dto.SignUpRequestDto

object SignUpMapper {
    fun mapSignUpToSignUpRequestDto(signUp: SignUp): SignUpRequestDto {
        return SignUpRequestDto(
            email = signUp.email,
            password = signUp.password,
            name = signUp.name,
        )
    }
}
