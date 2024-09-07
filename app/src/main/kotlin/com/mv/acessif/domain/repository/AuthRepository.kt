package com.mv.acessif.domain.repository

import com.mv.acessif.domain.AccessToken
import com.mv.acessif.domain.Login
import com.mv.acessif.domain.SignUp
import com.mv.acessif.domain.returnModel.DataError
import com.mv.acessif.domain.returnModel.Result

interface AuthRepository {
    suspend fun login(login: Login): Result<AccessToken, DataError.Network>

    suspend fun signUp(signUp: SignUp): Result<AccessToken, DataError.Network>
}
