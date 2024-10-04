package com.mv.acessif

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mv.acessif.domain.returnModel.Result
import com.mv.acessif.domain.useCase.RefreshTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel(
    private val refreshTokenUseCase: RefreshTokenUseCase,
    private val dispatcher: CoroutineDispatcher,
) : ViewModel() {
    @Inject
    constructor(
        refreshTokenUseCase: RefreshTokenUseCase,
    ) : this(
        refreshTokenUseCase = refreshTokenUseCase,
        dispatcher = Dispatchers.IO,
    )

    var isLoading = mutableStateOf(true)
        private set

    var isLoggedIn = mutableStateOf(false)
        private set

    init {
        checkRefreshToken()
    }

    private fun checkRefreshToken() {
        isLoading.value = true
        viewModelScope.launch {
            when (refreshTokenUseCase.execute()) {
                is Result.Success -> {
                    isLoggedIn.value = true
                }

                is Result.Error -> {
                    isLoggedIn.value = false
                }
            }

            isLoading.value = false
        }
    }
}
