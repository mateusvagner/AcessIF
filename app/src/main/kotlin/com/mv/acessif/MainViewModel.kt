package com.mv.acessif

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mv.acessif.domain.returnModel.Result
import com.mv.acessif.domain.useCase.RefreshTokenUseCase
import com.mv.acessif.presentation.home.home.HomeGraph
import com.mv.acessif.presentation.navigation.Navigator
import com.mv.acessif.presentation.root.RootGraph
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
    @Inject
    constructor(
        private val refreshTokenUseCase: RefreshTokenUseCase,
        navigator: Navigator,
    ) : ViewModel(), Navigator by navigator {

        private val _isLoading = MutableStateFlow(true)
        val isLoading = _isLoading.asStateFlow()

        init {
            checkRefreshToken()
        }

        private fun checkRefreshToken() {
            _isLoading.value = true
            viewModelScope.launch {
                val result = refreshTokenUseCase.execute()
                if (result is Result.Success) {
                    navigateToHome(result.data.name)
                }

                delay(300)

                _isLoading.value = false
            }
        }

        private suspend fun navigateToHome(userName: String) {
            navigateTo(HomeGraph.HomeRoute(userName = userName)) {
                popUpTo<RootGraph.WelcomeRoute> {
                    inclusive = true
                }
            }
        }
    }
