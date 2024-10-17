package com.mv.acessif.data.mapper

import com.mv.acessif.domain.User
import com.mv.acessif.network.dto.UserDto

object UserMapper {
    fun mapUserDtoToUser(userDto: UserDto): User {
        return User(
            id = userDto.id,
            name = userDto.name,
            email = userDto.email,
        )
    }
}
