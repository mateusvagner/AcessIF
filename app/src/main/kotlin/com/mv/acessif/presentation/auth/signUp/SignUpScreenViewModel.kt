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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpScreenViewModel
    @Inject
    constructor(
        private val signUpUseCase: SignUpUseCase,
    ) : ViewModel() {
        var signUpScreenState by mutableStateOf(SignUpScreenState())
            private set

        private val _onSignupSuccess = Channel<Unit>()
        val onSignupSuccess =
            _onSignupSuccess.receiveAsFlow().shareIn(viewModelScope, SharingStarted.Lazily)

        fun onNameChanged(name: String) {
            signUpScreenState =
                signUpScreenState.copy(
                    nameTextFieldState =
                        signUpScreenState.nameTextFieldState.copy(
                            value = name,
                            isError = false,
                        ),
                )
        }

        fun onEmailChanged(email: String) {
            signUpScreenState =
                signUpScreenState.copy(
                    emailTextFieldState =
                        signUpScreenState.emailTextFieldState.copy(
                            email = email,
                            isError = false,
                        ),
                )
        }

        fun onPasswordChanged(password: String) {
            signUpScreenState =
                signUpScreenState.copy(
                    passwordTextFieldState =
                        signUpScreenState.passwordTextFieldState.copy(
                            password = password,
                            isError = false,
                        ),
                )
        }

        fun onTogglePasswordVisibility() {
            signUpScreenState =
                signUpScreenState.copy(
                    passwordTextFieldState =
                        signUpScreenState.passwordTextFieldState.copy(
                            isVisible = !signUpScreenState.passwordTextFieldState.isVisible,
                        ),
                )
        }

        fun onSignUpPressed() {
            if (isNameValid(signUpScreenState.nameTextFieldState.value) &&
                isEmailValid(signUpScreenState.emailTextFieldState.email) &&
                isPasswordValid(signUpScreenState.passwordTextFieldState.password)
            ) {
                val signupBody =
                    SignUp(
                        name = signUpScreenState.nameTextFieldState.value,
                        email = signUpScreenState.emailTextFieldState.email,
                        password = signUpScreenState.passwordTextFieldState.password,
                    )

                viewModelScope.launch {
                    val result =
                        signUpUseCase.execute(
                            signUp = signupBody,
                        )

                    when (result) {
                        is Result.Success -> {
                            _onSignupSuccess.send(Unit)
                        }

                        is Result.Error -> {
                            signUpScreenState =
                                signUpScreenState.copy(
                                    signUpError = result.asErrorUiText(),
                                )
                        }
                    }
                }
            }
        }

        private fun isNameValid(name: String): Boolean {
            if (name.isEmpty()) {
                signUpScreenState =
                    signUpScreenState.copy(
                        nameTextFieldState =
                            signUpScreenState.nameTextFieldState.copy(
                                isError = true,
                                nameError = NameError.EMPTY,
                            ),
                    )
                return false
            }

            if (name.length < 3) {
                signUpScreenState =
                    signUpScreenState.copy(
                        nameTextFieldState =
                            signUpScreenState.nameTextFieldState.copy(
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
                signUpScreenState =
                    signUpScreenState.copy(
                        emailTextFieldState =
                            signUpScreenState.emailTextFieldState.copy(
                                isError = true,
                                emailError = EmailError.EMPTY,
                            ),
                    )
                return false
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                signUpScreenState =
                    signUpScreenState.copy(
                        emailTextFieldState =
                            signUpScreenState.emailTextFieldState.copy(
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
                signUpScreenState =
                    signUpScreenState.copy(
                        passwordTextFieldState =
                            signUpScreenState.passwordTextFieldState.copy(
                                isError = true,
                                passwordError = PasswordError.EMPTY,
                            ),
                    )
                return false
            }

            return true
        }
    }
