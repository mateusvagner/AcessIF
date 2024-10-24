package com.mv.acessif.data.repository

import com.mv.acessif.data.mapper.ErrorMapper
import com.mv.acessif.data.mapper.UserMapper
import com.mv.acessif.domain.User
import com.mv.acessif.domain.repository.UserRepository
import com.mv.acessif.domain.returnModel.DataError
import com.mv.acessif.domain.returnModel.Result
import com.mv.acessif.network.service.UserService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImpl
    @Inject
    constructor(
        private val userService: UserService,
    ) : UserRepository {
        override suspend fun getUser(): Result<User, DataError> {
            return withContext(Dispatchers.IO) {
                try {
                    val user = userService.getUser()
                    Result.Success(
                        UserMapper.mapUserDtoToUser(user),
                    )
                } catch (e: Exception) {
                    Result.Error(
                        ErrorMapper.mapNetworkExceptionToNetworkDataError(e),
                    )
                }
            }
        }

        override suspend fun logout(): Result<Unit, DataError> {
            return withContext(Dispatchers.IO) {
                try {
                    userService.logout()
                    Result.Success(Unit)
                } catch (e: Exception) {
                    Result.Error(
                        ErrorMapper.mapNetworkExceptionToNetworkDataError(e),
                    )
                }
            }
        }
    }
