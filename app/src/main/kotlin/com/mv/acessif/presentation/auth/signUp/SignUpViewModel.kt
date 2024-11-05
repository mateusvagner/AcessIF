package com.mv.acessif.presentation.auth.signUp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mv.acessif.domain.SignUp
import com.mv.acessif.domain.result.Result
import com.mv.acessif.domain.useCase.SignUpUseCase
import com.mv.acessif.presentation.asUiText
import com.mv.acessif.presentation.auth.commonState.EmailValidator
import com.mv.acessif.presentation.auth.commonState.NameValidator
import com.mv.acessif.presentation.auth.commonState.PasswordValidator
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
class SignUpViewModel
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

        private fun fieldsAreValid(): Boolean {
            _state.value =
                _state.value.copy(
                    emailTextFieldState =
                        _state.value.emailTextFieldState.validate(
                            EmailValidator,
                        ),
                    nameTextFieldState =
                        _state.value.nameTextFieldState.validate(
                            NameValidator,
                        ),
                    passwordTextFieldState =
                        _state.value.passwordTextFieldState.validate(
                            PasswordValidator,
                        ),
                )

            return _state.value.emailTextFieldState.errorMessage == null &&
                _state.value.nameTextFieldState.errorMessage == null &&
                _state.value.passwordTextFieldState.errorMessage == null
        }

        private fun onNameChanged(name: String) {
            _state.value =
                _state.value.copy(
                    nameTextFieldState =
                        _state.value.nameTextFieldState.copy(
                            value = name,
                            errorMessage = null,
                        ),
                )
        }

        private fun onEmailChanged(email: String) {
            _state.value =
                _state.value.copy(
                    emailTextFieldState =
                        _state.value.emailTextFieldState.copy(
                            value = email,
                            errorMessage = null,
                        ),
                )
        }

        private fun onPasswordChanged(password: String) {
            _state.value =
                _state.value.copy(
                    passwordTextFieldState =
                        _state.value.passwordTextFieldState.copy(
                            value = password,
                            errorMessage = null,
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
            if (fieldsAreValid()) {
                val signupBody =
                    SignUp(
                        name = _state.value.nameTextFieldState.value,
                        email = _state.value.emailTextFieldState.value,
                        password = _state.value.passwordTextFieldState.value,
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
                                signUpError = userResult.error.asUiText(),
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
