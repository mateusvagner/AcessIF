package com.mv.acessif.presentation.auth.signUp

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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpScreenViewModel
    @Inject
    constructor(
        private val signUpUseCase: SignUpUseCase,
        navigator: Navigator,
    ) : ViewModel(), Navigator by navigator {
        private val _state = MutableStateFlow(SignUpScreenState())
        val state =
            _state
                .stateIn(
                    viewModelScope,
                    SharingStarted.WhileSubscribed(5000L),
                    initialValue = SignUpScreenState(),
                )

        private fun isNameValid(name: String): Boolean {
            if (name.isEmpty()) {
                _state.value =
                    _state.value.copy(
                        nameTextFieldState =
                            _state.value.nameTextFieldState.copy(
                                isError = true,
                                nameError = NameError.EMPTY,
                            ),
                    )
                return false
            }

            if (name.length < 3) {
                _state.value =
                    _state.value.copy(
                        nameTextFieldState =
                            _state.value.nameTextFieldState.copy(
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

        private fun onNameChanged(name: String) {
            _state.value =
                _state.value.copy(
                    nameTextFieldState =
                        _state.value.nameTextFieldState.copy(
                            value = name,
                            isError = false,
                        ),
                )
        }

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

        private suspend fun onSignupPressed() {
            if (isNameValid(_state.value.nameTextFieldState.value) &&
                isEmailValid(_state.value.emailTextFieldState.email) &&
                isPasswordValid(_state.value.passwordTextFieldState.password)
            ) {
                val signupBody =
                    SignUp(
                        name = _state.value.nameTextFieldState.value,
                        email = _state.value.emailTextFieldState.email,
                        password = _state.value.passwordTextFieldState.password,
                    )

                _state.value =
                    _state.value.copy(
                        isLoading = true,
                    )

                val userResult =
                    signUpUseCase.execute(
                        signUp = signupBody,
                    )

                when (userResult) {
                    is Result.Success -> {
                        _state.value =
                            _state.value.copy(
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
                        _state.value =
                            _state.value.copy(
                                isLoading = false,
                                signUpError = userResult.asErrorUiText(),
                            )
                    }
                }
            }
        }

        private fun onTryAgain() {
            _state.value =
                _state.value.copy(
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
