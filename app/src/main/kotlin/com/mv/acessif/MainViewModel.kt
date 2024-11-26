package com.mv.acessif

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mv.acessif.data.repository.SharedPreferencesRepository
import com.mv.acessif.data.repository.UserRepository
import com.mv.acessif.domain.result.Result
import com.mv.acessif.presentation.home.home.HomeGraph
import com.mv.acessif.presentation.navigation.Navigator
import com.mv.acessif.presentation.root.RootGraph
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
    @Inject
    constructor(
        private val userRepository: UserRepository,
        private val sharedPreferencesRepository: SharedPreferencesRepository,
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
                val accessTokenResult = sharedPreferencesRepository.getAccessToken()

                if (accessTokenResult is Result.Success && accessTokenResult.data.isNotEmpty()) {
                    val userResult = userRepository.getUser()
                    if (userResult is Result.Success) {
                        navigateToHome(userResult.data.name)
                    }
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
