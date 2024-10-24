package com.mv.acessif.presentation.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mv.acessif.domain.Login
import com.mv.acessif.domain.returnModel.Result
import com.mv.acessif.domain.useCase.LoginUseCase
import com.mv.acessif.presentation.asErrorUiText
import com.mv.acessif.presentation.auth.commonState.EmailError
import com.mv.acessif.presentation.auth.commonState.PasswordError
import com.mv.acessif.presentation.home.home.HomeGraph
import com.mv.acessif.presentation.navigation.Navigator
import com.mv.acessif.presentation.root.RootGraph
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel
@Inject
constructor(
    private val loginUseCase: LoginUseCase,
    navigator: Navigator,
) : ViewModel(), Navigator by navigator {

    private val _state = MutableStateFlow(LoginScreenState())
    val state = _state
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            initialValue = LoginScreenState(),
        )

    private fun onEmailChanged(email: String) {
        _state.value =
            _state.value.copy(
                emailTextFieldState =
                _state.value.emailTextFieldState.copy(
                    email = email,
                    isError = false,
                ),
            )
    }

    private fun onPasswordChanged(password: String) {
        _state.value =
            _state.value.copy(
                passwordTextFieldState =
                _state.value.passwordTextFieldState.copy(
                    password = password,
                    isError = false,
                ),
            )
    }

    private fun onTogglePasswordVisibility() {
        _state.value =
            _state.value.copy(
                passwordTextFieldState =
                _state.value.passwordTextFieldState.copy(
                    isVisible = !_state.value.passwordTextFieldState.isVisible,
                ),
            )
    }

    private fun onSigninPressed() {
        if (isEmailValid(_state.value.emailTextFieldState.email) &&
            isPasswordValid(_state.value.passwordTextFieldState.password)
        ) {
            val loginBody =
                Login(
                    email = _state.value.emailTextFieldState.email,
                    password = _state.value.passwordTextFieldState.password,
                )

            viewModelScope.launch {
                _state.value =
                    _state.value.copy(
                        isLoading = true,
                    )
                val userResult =
                    loginUseCase.execute(login = loginBody)

                when (userResult) {
                    is Result.Success -> {
                        _state.value =
                            _state.value.copy(
                                isLoading = false,
                                signinError = null,
                            )

                        navigateTo(HomeGraph.HomeRoute(userName = userResult.data.name)) {
                            popUpTo<RootGraph.WelcomeRoute> {
                                inclusive = true
                            }
                        }
                    }

                    is Result.Error -> {
                        _state.value =
                            _state.value.copy(
                                isLoading = false,
                                signinError = userResult.asErrorUiText(),
                            )
                    }
                }
            }
        }
    }

    private fun isEmailValid(email: String): Boolean {
        if (email.isEmpty()) {
            _state.value =
                _state.value.copy(
                    emailTextFieldState =
                    _state.value.emailTextFieldState.copy(
                        isError = true,
                        emailError = EmailError.EMPTY,
                    ),
                )
            return false
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _state.value =
                _state.value.copy(
                    emailTextFieldState =
                    _state.value.emailTextFieldState.copy(
                        isError = true,
                        emailError = EmailError.INVALID,
                    ),
                )
            return false
        }

        return true
    }

    private fun isPasswordValid(password: String): Boolean {
        if (password.isEmpty()) {
            _state.value =
                _state.value.copy(
                    passwordTextFieldState =
                    _state.value.passwordTextFieldState.copy(
                        isError = true,
                        passwordError = PasswordError.EMPTY,
                    ),
                )
            return false
        }

        return true
    }

    private fun onTryAgain() {
        _state.value =
            _state.value.copy(
                signinError = null,
                isLoading = false,
            )
    }

    fun handleIntent(loginScreenIntent: LoginScreenIntent) {
        viewModelScope.launch {
            when (loginScreenIntent) {
                is LoginScreenIntent.OnEmailChanged -> {
                    onEmailChanged(loginScreenIntent.email)
                }

                is LoginScreenIntent.OnPasswordChanged -> {
                    onPasswordChanged(loginScreenIntent.password)
                }

                LoginScreenIntent.OnTogglePasswordVisibility -> {
                    onTogglePasswordVisibility()
                }

                LoginScreenIntent.OnSigninPressed -> {
                    onSigninPressed()
                }

                LoginScreenIntent.OnSignUpPressed -> {
                    navigateTo(RootGraph.SignUpRoute) {
                        popUpTo<RootGraph.LoginRoute> {
                            inclusive = true
                        }
                    }
                }

                LoginScreenIntent.OnNavigateBack -> {
                    navigateUp()
                }

                LoginScreenIntent.OnTryAgain -> {
                    onTryAgain()
                }
            }
        }
    }
}
