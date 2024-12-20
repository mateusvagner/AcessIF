package com.mv.acessif.data.repository.impl

import com.mv.acessif.data.mapper.AccessTokenMapper
import com.mv.acessif.data.mapper.AuthTokenMapper
import com.mv.acessif.data.mapper.ErrorMapper
import com.mv.acessif.data.mapper.LoginMapper
import com.mv.acessif.data.mapper.SignUpMapper
import com.mv.acessif.data.repository.AuthRepository
import com.mv.acessif.domain.AccessToken
import com.mv.acessif.domain.AuthToken
import com.mv.acessif.domain.Login
import com.mv.acessif.domain.SignUp
import com.mv.acessif.domain.result.DataError
import com.mv.acessif.domain.result.Result
import com.mv.acessif.network.service.AuthService
import com.mv.acessif.util.DispatcherProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRepositoryImpl
    @Inject
    constructor(
        private val authService: AuthService,
        private val dispatcherProvider: DispatcherProvider,
    ) : AuthRepository {
        override suspend fun login(login: Login): Result<AuthToken, DataError.Network> {
            val loginRequestDto = LoginMapper.mapLoginToLoginRequestDto(login)
            return withContext(dispatcherProvider.io) {
                try {
                    val authTokenDto = authService.postLogin(loginRequestDto)
                    val authToken = AuthTokenMapper.mapAuthTokenDtoToAuthToken(authTokenDto)
                    Result.Success(authToken)
                } catch (e: Exception) {
                    Result.Error(
                        ErrorMapper.mapNetworkExceptionToNetworkDataError(e),
                    )
                }
            }
        }

        override suspend fun signUp(signUp: SignUp): Result<AuthToken, DataError.Network> {
            val signUpRequestDto = SignUpMapper.mapSignUpToSignUpRequestDto(signUp)
            return withContext(dispatcherProvider.io) {
                try {
                    val authTokenDto = authService.postSignUp(signUpRequestDto)
                    val authToken = AuthTokenMapper.mapAuthTokenDtoToAuthToken(authTokenDto)
                    Result.Success(authToken)
                } catch (e: Exception) {
                    Result.Error(
                        ErrorMapper.mapNetworkExceptionToNetworkDataError(e),
                    )
                }
            }
        }

        override suspend fun refreshToken(refreshToken: String): Result<AccessToken, DataError.Network> {
            return withContext(dispatcherProvider.io) {
                try {
                    val accessTokenDto = authService.postRefreshToken(refreshToken)
                    val accessToken = AccessTokenMapper.mapAccessTokenDtoToAccessToken(accessTokenDto)
                    Result.Success(accessToken)
                } catch (e: Exception) {
                    Result.Error(
                        ErrorMapper.mapNetworkExceptionToNetworkDataError(e),
                    )
                }
            }
        }
    }
