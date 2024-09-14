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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel(
    private val loginUseCase: LoginUseCase,
    private val dispatcher: CoroutineDispatcher,
) : ViewModel() {
    @Inject
    constructor(
        loginUseCase: LoginUseCase,
    ) : this(
        loginUseCase = loginUseCase,
        dispatcher = Dispatchers.IO,
    )

    var loginScreenState by mutableStateOf(LoginScreenState())
        private set

    private val _onSigninSuccess = Channel<Unit>()
    val onSigninSuccess =
        _onSigninSuccess.receiveAsFlow().shareIn(viewModelScope, SharingStarted.Lazily)

    fun onEmailChanged(email: String) {
        loginScreenState =
            loginScreenState.copy(
                emailTextFieldState =
                    loginScreenState.emailTextFieldState.copy(
                        email = email,
                        isError = false,
                    ),
            )
    }

    fun onPasswordChanged(password: String) {
        loginScreenState =
            loginScreenState.copy(
                passwordTextFieldState =
                    loginScreenState.passwordTextFieldState.copy(
                        password = password,
                        isError = false,
                    ),
            )
    }

    fun onTogglePasswordVisibility() {
        loginScreenState =
            loginScreenState.copy(
                passwordTextFieldState =
                    loginScreenState.passwordTextFieldState.copy(
                        isVisible = !loginScreenState.passwordTextFieldState.isVisible,
                    ),
            )
    }

    fun onSigninPressed() {
        if (isEmailValid(loginScreenState.emailTextFieldState.email) &&
            isPasswordValid(loginScreenState.passwordTextFieldState.password)
        ) {
            val loginBody =
                Login(
                    email = loginScreenState.emailTextFieldState.email,
                    password = loginScreenState.passwordTextFieldState.password,
                )

            viewModelScope.launch(dispatcher) {
                val result =
                    loginUseCase.execute(login = loginBody)

                when (result) {
                    is Result.Success -> {
                        _onSigninSuccess.send(Unit)
                    }

                    is Result.Error -> {
                        loginScreenState =
                            loginScreenState.copy(
                                signinError = result.asErrorUiText(),
                            )
                    }
                }
            }
        }
    }

    fun onTryAgain() {
        loginScreenState =
            loginScreenState.copy(
                signinError = null,
                isLoading = false,
            )
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
}
