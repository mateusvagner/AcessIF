package com.mv.acessif.data.repository

import com.mv.acessif.data.mapper.AccessTokenMapper
import com.mv.acessif.data.mapper.ErrorMapper
import com.mv.acessif.data.mapper.LoginMapper
import com.mv.acessif.data.mapper.SignUpMapper
import com.mv.acessif.domain.AccessToken
import com.mv.acessif.domain.Login
import com.mv.acessif.domain.SignUp
import com.mv.acessif.domain.repository.AuthRepository
import com.mv.acessif.domain.returnModel.DataError
import com.mv.acessif.domain.returnModel.Result
import com.mv.acessif.network.service.AuthService
import javax.inject.Inject

class AuthRepositoryImpl
    @Inject
    constructor(
        private val authService: AuthService,
    ) : AuthRepository {
        override suspend fun login(login: Login): Result<AccessToken, DataError.Network> {
            val loginRequestDto = LoginMapper.mapLoginToLoginRequestDto(login)
            return try {
                val accessTokenDto = authService.postLogin(loginRequestDto)
                val accessToken = AccessTokenMapper.mapAccessTokenDtoToAccessToken(accessTokenDto)
                Result.Success(accessToken)
            } catch (e: Exception) {
                Result.Error(
                    ErrorMapper.mapNetworkExceptionToNetworkDataError(e),
                )
            }
        }

        override suspend fun signUp(signUp: SignUp): Result<AccessToken, DataError.Network> {
            val signUpRequestDto = SignUpMapper.mapSignUpToSignUpRequestDto(signUp)
            return try {
                val accessTokenDto = authService.postSignUp(signUpRequestDto)
                val accessToken = AccessTokenMapper.mapAccessTokenDtoToAccessToken(accessTokenDto)
                Result.Success(accessToken)
            } catch (e: Exception) {
                Result.Error(
                    ErrorMapper.mapNetworkExceptionToNetworkDataError(e),
                )
            }
        }
    }
