package com.mv.acessif.presentation.auth.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel
    @Inject
    constructor(
        private val loginUseCase: LoginUseCase,
        navigator: Navigator,
    ) : ViewModel(), Navigator by navigator {
        var loginScreenState by mutableStateOf(LoginScreenState())
            private set

        private fun onEmailChanged(email: String) {
            loginScreenState =
                loginScreenState.copy(
                    emailTextFieldState =
                        loginScreenState.emailTextFieldState.copy(
                            email = email,
                            isError = false,
                        ),
                )
        }

        private fun onPasswordChanged(password: String) {
            loginScreenState =
                loginScreenState.copy(
                    passwordTextFieldState =
                        loginScreenState.passwordTextFieldState.copy(
                            password = password,
                            isError = false,
                        ),
                )
        }

        private fun onTogglePasswordVisibility() {
            loginScreenState =
                loginScreenState.copy(
                    passwordTextFieldState =
                        loginScreenState.passwordTextFieldState.copy(
                            isVisible = !loginScreenState.passwordTextFieldState.isVisible,
                        ),
                )
        }

        private fun onSigninPressed() {
            if (isEmailValid(loginScreenState.emailTextFieldState.email) &&
                isPasswordValid(loginScreenState.passwordTextFieldState.password)
            ) {
                val loginBody =
                    Login(
                        email = loginScreenState.emailTextFieldState.email,
                        password = loginScreenState.passwordTextFieldState.password,
                    )

                viewModelScope.launch {
                    loginScreenState =
                        loginScreenState.copy(
                            isLoading = true,
                        )
                    val userResult =
                        loginUseCase.execute(login = loginBody)

                    when (userResult) {
                        is Result.Success -> {
                            loginScreenState =
                                loginScreenState.copy(
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
                            loginScreenState =
                                loginScreenState.copy(
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
                loginScreenState =
                    loginScreenState.copy(
                        emailTextFieldState =
                            loginScreenState.emailTextFieldState.copy(
                                isError = true,
                                emailError = EmailError.EMPTY,
                            ),
                    )
                return false
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                loginScreenState =
                    loginScreenState.copy(
                        emailTextFieldState =
                            loginScreenState.emailTextFieldState.copy(
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
                loginScreenState =
                    loginScreenState.copy(
                        passwordTextFieldState =
                            loginScreenState.passwordTextFieldState.copy(
                                isError = true,
                                passwordError = PasswordError.EMPTY,
                            ),
                    )
                return false
            }

            return true
        }

        fun onTryAgain() {
            loginScreenState =
                loginScreenState.copy(
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
