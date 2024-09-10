package com.mv.acessif.presentation.auth.signUp

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
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.mv.acessif.ui.designSystem.components.ScreenHeader
import com.mv.acessif.ui.designSystem.components.textField.EmailTextField
import com.mv.acessif.ui.designSystem.components.textField.PasswordTextField
import com.mv.acessif.ui.designSystem.components.textField.SimpleTextField
import com.mv.acessif.ui.theme.BodyLarge
import com.mv.acessif.ui.theme.L
import com.mv.acessif.ui.theme.NeutralBackground
import com.mv.acessif.ui.theme.XL
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
            screenState = viewModel.signUpScreenState,
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

                    SignUpScreenIntent.OnSignUpPressed -> {
                        viewModel.onSignUpPressed()
                    }

                    SignUpScreenIntent.OnNavigateBack -> {
                        rootNavController.navigateUp()
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
                .background(color = NeutralBackground)
                .padding(bottom = XL),
    ) {
        ScreenHeader(
            screenTitle = stringResource(id = R.string.sign_up),
            onBackPressed = {
                onIntent(SignUpScreenIntent.OnNavigateBack)
            },
            origin = stringResource(id = R.string.welcome_screen),
        )

        // TODO Loading and Error

        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = L)
                    .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
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
                onVisibilityChanged = {
                    onIntent(SignUpScreenIntent.OnTogglePasswordVisibility)
                },
                onValueChange = {
                    onIntent(SignUpScreenIntent.OnPasswordChanged(it))
                },
            )

            Spacer(modifier = Modifier.height(XL))

            Button(
                onClick = {
                    onIntent(SignUpScreenIntent.OnSignUpPressed)
                },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(text = stringResource(id = R.string.sign_up))
            }

            // TODO Remove after create error state

            if (screenState.signUpError != null) {
                Text(
                    modifier = Modifier.padding(top = L),
                    text = screenState.signUpError.asString(),
                    style = BodyLarge.copy(color = Color.Red),
                )
            }
        }
    }
}

@Composable
@Preview
private fun SignUpScreenPreview() {
    Surface {
        SignUpScreen(
            modifier = Modifier,
            screenState = SignUpScreenState(),
            onIntent = {},
        )
    }
}

@Composable
@Preview
private fun SignUpScreenErrorPreview() {
    Surface {
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
