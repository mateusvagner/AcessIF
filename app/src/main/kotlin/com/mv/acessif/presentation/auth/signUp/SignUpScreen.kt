package com.mv.acessif.presentation.auth.signUp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.mv.acessif.ui.designSystem.components.DefaultScreenHeader
import com.mv.acessif.ui.designSystem.components.ErrorComponent
import com.mv.acessif.ui.designSystem.components.LoadingComponent
import com.mv.acessif.ui.designSystem.components.textField.EmailTextField
import com.mv.acessif.ui.designSystem.components.textField.PasswordTextField
import com.mv.acessif.ui.designSystem.components.textField.SimpleTextField
import com.mv.acessif.ui.theme.AcessIFTheme
import com.mv.acessif.ui.theme.BaseButtonHeight
import com.mv.acessif.ui.theme.BodyLarge
import com.mv.acessif.ui.theme.HeadlineLarge
import com.mv.acessif.ui.theme.L
import com.mv.acessif.ui.theme.XL
import com.mv.acessif.ui.theme.XXL
import kotlinx.serialization.Serializable

@Serializable
object SignUpScreen

fun NavGraphBuilder.signUpScreen(
    modifier: Modifier,
    rootNavController: NavHostController,
) {
    composable<SignUpScreen> {
        val viewModel: SignUpScreenViewModel = hiltViewModel()

        LaunchedEffect(key1 = Unit) {
            viewModel.onSignupSuccess.collect {
                rootNavController.navigate(HomeNavGraph) {
                    popUpTo<WelcomeScreen> {
                        inclusive = true
                    }
                }
            }
        }

        SignUpScreen(
            modifier = modifier,
            screenState = viewModel.signupScreenState,
            onIntent = { intent ->
                when (intent) {
                    is SignUpScreenIntent.OnNameChanged -> {
                        viewModel.onNameChanged(intent.name)
                    }

                    is SignUpScreenIntent.OnEmailChanged -> {
                        viewModel.onEmailChanged(intent.email)
                    }

                    is SignUpScreenIntent.OnPasswordChanged -> {
                        viewModel.onPasswordChanged(intent.password)
                    }

                    SignUpScreenIntent.OnTogglePasswordVisibility -> {
                        viewModel.onTogglePasswordVisibility()
                    }

                    SignUpScreenIntent.OnSignupPressed -> {
                        viewModel.onSignupPressed()
                    }

                    SignUpScreenIntent.OnNavigateBack -> {
                        rootNavController.navigateUp()
                    }

                    SignUpScreenIntent.OnTryAgain -> {
                        viewModel.onTryAgain()
                    }
                }
            },
        )
    }
}

@Composable
fun SignUpScreen(
    modifier: Modifier,
    screenState: SignUpScreenState,
    onIntent: (SignUpScreenIntent) -> Unit,
) {
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
                .padding(bottom = XL),
    ) {
        DefaultScreenHeader(
            modifier =
                Modifier
                    .background(color = MaterialTheme.colorScheme.primary),
            origin = stringResource(id = R.string.welcome_screen),
            onBackPressed = { onIntent(SignUpScreenIntent.OnNavigateBack) },
        )

        if (screenState.isLoading) {
            LoadingComponent(
                modifier = Modifier.fillMaxSize(),
                label = stringResource(id = R.string.creating_account),
            )
        } else if (screenState.signUpError != null) {
            ErrorComponent(
                modifier = Modifier.fillMaxSize(),
                message = screenState.signUpError.asString(),
                onTryAgain = {
                    onIntent(SignUpScreenIntent.OnTryAgain)
                },
            )
        } else {
            val focusManager = LocalFocusManager.current
            Column(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(horizontal = L)
                        .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center,
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

                SimpleTextField(
                    label = stringResource(id = R.string.name),
                    value = screenState.nameTextFieldState.value,
                    isError = screenState.nameTextFieldState.isError,
                    errorMessage =
                        if (screenState.nameTextFieldState.nameError != null) {
                            stringResource(screenState.nameTextFieldState.nameError.errorMessage)
                        } else {
                            null
                        },
                    focusManager = focusManager,
                ) {
                    onIntent(SignUpScreenIntent.OnNameChanged(it))
                }

                Spacer(modifier = Modifier.height(L))

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
                    focusManager = focusManager,
                ) {
                    onIntent(SignUpScreenIntent.OnEmailChanged(it))
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
                        onIntent(SignUpScreenIntent.OnTogglePasswordVisibility)
                    },
                    onValueChange = {
                        onIntent(SignUpScreenIntent.OnPasswordChanged(it))
                    },
                    onDone = {
                        onIntent(SignUpScreenIntent.OnSignupPressed)
                    },
                )

                Spacer(modifier = Modifier.height(XXL))

                Button(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .sizeIn(minHeight = BaseButtonHeight),
                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                        ),
                    onClick = { onIntent(SignUpScreenIntent.OnSignupPressed) },
                ) {
                    Text(
                        text = stringResource(id = R.string.sign_up),
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
private fun SignUpScreenPreview() {
    AcessIFTheme {
        SignUpScreen(
            modifier = Modifier,
            screenState = SignUpScreenState(),
            onIntent = {},
        )
    }
}

@Composable
@Preview
private fun SignUpScreenLoadingPreview() {
    AcessIFTheme {
        SignUpScreen(
            modifier = Modifier,
            screenState =
                SignUpScreenState(
                    isLoading = true,
                ),
            onIntent = {},
        )
    }
}

@Composable
@Preview
private fun SignUpScreenErrorPreview() {
    AcessIFTheme {
        SignUpScreen(
            modifier = Modifier,
            screenState =
                SignUpScreenState(
                    signUpError = DataError.Network.TIMEOUT.asUiText(),
                ),
            onIntent = {},
        )
    }
}
