package com.mv.acessif.presentation.auth.signUp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mv.acessif.domain.SignUp
import com.mv.acessif.domain.returnModel.Result
import com.mv.acessif.domain.useCase.SignUpUseCase
import com.mv.acessif.presentation.asErrorUiText
import com.mv.acessif.presentation.auth.commonState.EmailError
import com.mv.acessif.presentation.auth.commonState.NameError
import com.mv.acessif.presentation.auth.commonState.PasswordError
import com.mv.acessif.presentation.home.home.HomeGraph
import com.mv.acessif.presentation.navigation.Navigator
import com.mv.acessif.presentation.root.RootGraph
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpScreenViewModel
    @Inject
    constructor(
        private val signUpUseCase: SignUpUseCase,
        navigator: Navigator,
    ) : ViewModel(), Navigator by navigator {
        var signupScreenState by mutableStateOf(SignUpScreenState())
            private set

        private fun isNameValid(name: String): Boolean {
            if (name.isEmpty()) {
                signupScreenState =
                    signupScreenState.copy(
                        nameTextFieldState =
                            signupScreenState.nameTextFieldState.copy(
                                isError = true,
                                nameError = NameError.EMPTY,
                            ),
                    )
                return false
            }

            if (name.length < 3) {
                signupScreenState =
                    signupScreenState.copy(
                        nameTextFieldState =
                            signupScreenState.nameTextFieldState.copy(
                                isError = true,
                                nameError = NameError.SHORT,
                            ),
                    )
                return false
            }

            return true
        }

        private fun isEmailValid(email: String): Boolean {
            if (email.isEmpty()) {
                signupScreenState =
                    signupScreenState.copy(
                        emailTextFieldState =
                            signupScreenState.emailTextFieldState.copy(
                                isError = true,
                                emailError = EmailError.EMPTY,
                            ),
                    )
                return false
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                signupScreenState =
                    signupScreenState.copy(
                        emailTextFieldState =
                            signupScreenState.emailTextFieldState.copy(
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
                signupScreenState =
                    signupScreenState.copy(
                        passwordTextFieldState =
                            signupScreenState.passwordTextFieldState.copy(
                                isError = true,
                                passwordError = PasswordError.EMPTY,
                            ),
                    )
                return false
            }

            return true
        }

        private fun onNameChanged(name: String) {
            signupScreenState =
                signupScreenState.copy(
                    nameTextFieldState =
                        signupScreenState.nameTextFieldState.copy(
                            value = name,
                            isError = false,
                        ),
                )
        }

        private fun onEmailChanged(email: String) {
            signupScreenState =
                signupScreenState.copy(
                    emailTextFieldState =
                        signupScreenState.emailTextFieldState.copy(
                            email = email,
                            isError = false,
                        ),
                )
        }

        private fun onPasswordChanged(password: String) {
            signupScreenState =
                signupScreenState.copy(
                    passwordTextFieldState =
                        signupScreenState.passwordTextFieldState.copy(
                            password = password,
                            isError = false,
                        ),
                )
        }

        private fun onTogglePasswordVisibility() {
            signupScreenState =
                signupScreenState.copy(
                    passwordTextFieldState =
                        signupScreenState.passwordTextFieldState.copy(
                            isVisible = !signupScreenState.passwordTextFieldState.isVisible,
                        ),
                )
        }

        private fun onSignupPressed() {
            if (isNameValid(signupScreenState.nameTextFieldState.value) &&
                isEmailValid(signupScreenState.emailTextFieldState.email) &&
                isPasswordValid(signupScreenState.passwordTextFieldState.password)
            ) {
                val signupBody =
                    SignUp(
                        name = signupScreenState.nameTextFieldState.value,
                        email = signupScreenState.emailTextFieldState.email,
                        password = signupScreenState.passwordTextFieldState.password,
                    )

                viewModelScope.launch {
                    signupScreenState =
                        signupScreenState.copy(
                            isLoading = true,
                        )

                    val userResult =
                        signUpUseCase.execute(
                            signUp = signupBody,
                        )

                    when (userResult) {
                        is Result.Success -> {
                            signupScreenState =
                                signupScreenState.copy(
                                    isLoading = false,
                                    signUpError = null,
                                )

                            navigateTo(HomeGraph.HomeRoute(userName = userResult.data.name)) {
                                popUpTo<RootGraph.WelcomeRoute> {
                                    inclusive = true
                                }
                            }
                        }

                        is Result.Error -> {
                            signupScreenState =
                                signupScreenState.copy(
                                    isLoading = false,
                                    signUpError = userResult.asErrorUiText(),
                                )
                        }
                    }
                }
            }
        }

        private fun onTryAgain() {
            signupScreenState =
                signupScreenState.copy(
                    signUpError = null,
                    isLoading = false,
                )
        }

        fun handleIntent(intent: SignUpScreenIntent) {
            viewModelScope.launch {
                when (intent) {
                    is SignUpScreenIntent.OnNameChanged -> {
                        onNameChanged(intent.name)
                    }

                    is SignUpScreenIntent.OnEmailChanged -> {
                        onEmailChanged(intent.email)
                    }

                    is SignUpScreenIntent.OnPasswordChanged -> {
                        onPasswordChanged(intent.password)
                    }

                    SignUpScreenIntent.OnTogglePasswordVisibility -> {
                        onTogglePasswordVisibility()
                    }

                    SignUpScreenIntent.OnSignupPressed -> {
                        onSignupPressed()
                    }

                    SignUpScreenIntent.OnNavigateBack -> {
                        navigateUp()
                    }

                    SignUpScreenIntent.OnTryAgain -> {
                        onTryAgain()
                    }
                }
            }
        }
    }
