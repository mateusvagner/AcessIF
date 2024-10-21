package com.mv.acessif.network.service

import com.mv.acessif.network.dto.UserDto

interface UserService {
    suspend fun getUser(): UserDto

    suspend fun logout()
}
