package com.mv.acessif.presentation.auth.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.mv.acessif.R
import com.mv.acessif.domain.returnModel.DataError
import com.mv.acessif.presentation.asUiText
import com.mv.acessif.presentation.home.home.HomeNavGraph
import com.mv.acessif.presentation.root.welcome.WelcomeScreen
import com.mv.acessif.ui.designSystem.components.ErrorComponent
import com.mv.acessif.ui.designSystem.components.LoadingComponent
import com.mv.acessif.ui.designSystem.components.ScreenHeader
import com.mv.acessif.ui.designSystem.components.button.MainActionButton
import com.mv.acessif.ui.designSystem.components.textField.EmailTextField
import com.mv.acessif.ui.designSystem.components.textField.PasswordTextField
import com.mv.acessif.ui.theme.AcessIFTheme
import com.mv.acessif.ui.theme.L
import com.mv.acessif.ui.theme.NeutralBackground
import com.mv.acessif.ui.theme.XL
import kotlinx.serialization.Serializable

@Serializable
object LoginScreen

fun NavGraphBuilder.loginScreen(
    modifier: Modifier,
    rootNavController: NavHostController,
) {
    composable<LoginScreen> {
        val viewModel: LoginScreenViewModel = hiltViewModel()

        LaunchedEffect(key1 = Unit) {
            viewModel.onSigninSuccess.collect {
                rootNavController.navigate(HomeNavGraph) {
                    popUpTo<WelcomeScreen> {
                        inclusive = true
                    }
                }
            }
        }

        LoginScreen(
            modifier = modifier,
            screenState = viewModel.loginScreenState,
            onIntent = {
                when (it) {
                    is LoginScreenIntent.OnEmailChanged -> {
                        viewModel.onEmailChanged(it.email)
                    }

                    is LoginScreenIntent.OnPasswordChanged -> {
                        viewModel.onPasswordChanged(it.password)
                    }

                    LoginScreenIntent.OnTogglePasswordVisibility -> {
                        viewModel.onTogglePasswordVisibility()
                    }

                    LoginScreenIntent.OnSigninPressed -> {
                        viewModel.onSigninPressed()
                    }

                    LoginScreenIntent.OnNavigateBack -> {
                        rootNavController.navigateUp()
                    }

                    LoginScreenIntent.OnTryAgain -> {
                        viewModel.onTryAgain()
                    }
                }
            },
        )
    }
}

@Composable
fun LoginScreen(
    modifier: Modifier,
    screenState: LoginScreenState,
    onIntent: (LoginScreenIntent) -> Unit,
) {
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .background(color = NeutralBackground)
                .padding(bottom = XL),
    ) {
        ScreenHeader(
            screenTitle = stringResource(id = R.string.sign_in),
            onBackPressed = {
                onIntent(LoginScreenIntent.OnNavigateBack)
            },
            origin = stringResource(id = R.string.welcome_screen),
        )

        if (screenState.isLoading) {
            LoadingComponent(
                modifier = Modifier.fillMaxSize(),
                label = stringResource(id = R.string.signing_in),
            )
        } else if (screenState.signinError != null) {
            ErrorComponent(
                modifier = Modifier.fillMaxSize(),
                message = screenState.signinError.asString(),
                onTryAgain = {
                    onIntent(LoginScreenIntent.OnTryAgain)
                },
            )
        } else {
            Column(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(horizontal = L)
                        .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                EmailTextField(
                    label = stringResource(id = R.string.email),
                    email = screenState.emailTextFieldState.email,
                    isError = screenState.emailTextFieldState.isError,
                    errorMessage =
                        if (screenState.emailTextFieldState.emailError != null) {
                            stringResource(screenState.emailTextFieldState.emailError.errorMessage)
                        } else {
                            null
                        },
                ) {
                    onIntent(LoginScreenIntent.OnEmailChanged(it))
                }

                Spacer(modifier = Modifier.height(L))

                PasswordTextField(
                    label = stringResource(id = R.string.password),
                    password = screenState.passwordTextFieldState.password,
                    isVisible = screenState.passwordTextFieldState.isVisible,
                    isError = screenState.passwordTextFieldState.isError,
                    errorMessage =
                        if (screenState.passwordTextFieldState.passwordError != null) {
                            stringResource(screenState.passwordTextFieldState.passwordError.errorMessage)
                        } else {
                            null
                        },
                    onVisibilityChanged = {
                        onIntent(LoginScreenIntent.OnTogglePasswordVisibility)
                    },
                    onValueChange = {
                        onIntent(LoginScreenIntent.OnPasswordChanged(it))
                    },
                )

                Spacer(modifier = Modifier.height(XL))

                MainActionButton(
                    modifier = Modifier.fillMaxWidth(),
                    label = stringResource(id = R.string.sign_in),
                    onClick = {
                        onIntent(LoginScreenIntent.OnSigninPressed)
                    },
                )
            }
        }
    }
}

@Composable
@Preview
private fun LoginScreenPreview() {
    AcessIFTheme {
        LoginScreen(
            modifier = Modifier,
            screenState = LoginScreenState(),
            onIntent = {},
        )
    }
}

@Composable
@Preview
private fun LoginScreenLoadingPreview() {
    AcessIFTheme {
        LoginScreen(
            modifier = Modifier,
            screenState =
                LoginScreenState(
                    isLoading = true,
                ),
            onIntent = {},
        )
    }
}

@Composable
@Preview
private fun LoginScreenErrorPreview() {
    AcessIFTheme {
        LoginScreen(
            modifier = Modifier,
            screenState =
                LoginScreenState(
                    signinError = DataError.Network.TIMEOUT.asUiText(),
                ),
            onIntent = {},
        )
    }
}
