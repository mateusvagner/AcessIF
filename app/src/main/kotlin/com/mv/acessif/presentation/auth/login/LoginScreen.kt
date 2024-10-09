package com.mv.acessif.presentation.auth.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.mv.acessif.R
import com.mv.acessif.domain.returnModel.DataError
import com.mv.acessif.presentation.asUiText
import com.mv.acessif.presentation.home.home.HomeNavGraph
import com.mv.acessif.presentation.root.welcome.WelcomeScreen
import com.mv.acessif.ui.designSystem.components.CustomButton
import com.mv.acessif.ui.designSystem.components.DefaultScreenHeader
import com.mv.acessif.ui.designSystem.components.ErrorComponent
import com.mv.acessif.ui.designSystem.components.LoadingComponent
import com.mv.acessif.ui.designSystem.components.textField.EmailTextField
import com.mv.acessif.ui.designSystem.components.textField.PasswordTextField
import com.mv.acessif.ui.theme.AcessIFTheme
import com.mv.acessif.ui.theme.BodyLarge
import com.mv.acessif.ui.theme.HeadlineLarge
import com.mv.acessif.ui.theme.L
import com.mv.acessif.ui.theme.XXL
import com.mv.acessif.ui.theme.XXXL
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
                .background(color = MaterialTheme.colorScheme.background),
    ) {
        Box {
            Image(
                modifier =
                    Modifier
                        .align(alignment = Alignment.TopStart)
                        .offset(x = (-45).dp, y = 50.dp),
                painter = painterResource(R.drawable.img_moon_background),
                contentDescription = null,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1F)),
            )

            Image(
                modifier =
                    Modifier
                        .align(alignment = Alignment.TopEnd)
                        .offset(x = 45.dp, y = 50.dp),
                painter = painterResource(R.drawable.img_moon_background),
                contentDescription = null,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1F)),
            )

            Image(
                modifier =
                    Modifier
                        .align(alignment = Alignment.BottomEnd)
                        .offset(x = 45.dp, y = 45.dp),
                painter = painterResource(R.drawable.img_moon_background),
                contentDescription = null,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1F)),
            )

            Image(
                modifier =
                    Modifier
                        .align(alignment = Alignment.BottomStart)
                        .offset(x = (-45).dp, y = 45.dp),
                painter = painterResource(R.drawable.img_moon_background),
                contentDescription = null,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1F)),
            )

            MainContent(
                modifier = Modifier.fillMaxSize(),
                screenState = screenState,
                onIntent = onIntent,
            )
        }
    }
}

@Composable
fun MainContent(
    modifier: Modifier = Modifier,
    screenState: LoginScreenState,
    onIntent: (LoginScreenIntent) -> Unit,
) {
    Column(
        modifier = modifier,
    ) {
        DefaultScreenHeader(
            origin = stringResource(id = R.string.welcome_screen),
            onBackPressed = { onIntent(LoginScreenIntent.OnNavigateBack) },
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
            val focusManager = LocalFocusManager.current
            Column(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(horizontal = L)
                        .padding(top = XXXL)
                        .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painter = painterResource(R.drawable.img_moon),
                    colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.primary),
                    contentDescription = null,
                )

                Spacer(modifier = Modifier.height(L))

                Text(
                    text = stringResource(R.string.welcome),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = HeadlineLarge,
                )

                Spacer(modifier = Modifier.height(64.dp))

                EmailTextField(
                    label = stringResource(id = R.string.email),
                    email = screenState.emailTextFieldState.email,
                    isError = screenState.emailTextFieldState.isError,
                    focusManager = focusManager,
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
                    focusManager = focusManager,
                    onVisibilityChanged = {
                        onIntent(LoginScreenIntent.OnTogglePasswordVisibility)
                    },
                    onValueChange = {
                        onIntent(LoginScreenIntent.OnPasswordChanged(it))
                    },
                    onDone = {
                        onIntent(LoginScreenIntent.OnSigninPressed)
                    },
                )

                Spacer(modifier = Modifier.height(XXL))

                CustomButton(
                    modifier = Modifier.fillMaxWidth(),
                    isLightColor = false,
                    color = MaterialTheme.colorScheme.primary,
                    onClick = { onIntent(LoginScreenIntent.OnSigninPressed) },
                ) {
                    Text(
                        text = stringResource(id = R.string.sign_in),
                        style = BodyLarge.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                }
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
