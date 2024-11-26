package com.mv.acessif.presentation.auth.signUp

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
import androidx.compose.runtime.getValue
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mv.acessif.R
import com.mv.acessif.domain.result.DataError
import com.mv.acessif.presentation.asUiText
import com.mv.acessif.presentation.root.RootGraph
import com.mv.acessif.ui.designSystem.components.DefaultScreenHeader
import com.mv.acessif.ui.designSystem.components.ErrorComponent
import com.mv.acessif.ui.designSystem.components.LoadingComponent
import com.mv.acessif.ui.designSystem.components.button.CustomButton
import com.mv.acessif.ui.designSystem.components.textField.EmailTextField
import com.mv.acessif.ui.designSystem.components.textField.PasswordTextField
import com.mv.acessif.ui.designSystem.components.textField.SimpleTextField
import com.mv.acessif.ui.theme.AcessIFTheme
import com.mv.acessif.ui.theme.BodyLarge
import com.mv.acessif.ui.theme.HeadlineLarge
import com.mv.acessif.ui.theme.L
import com.mv.acessif.ui.theme.XXL
import com.mv.acessif.ui.theme.XXXL

fun NavGraphBuilder.signUpRoute(modifier: Modifier) {
    composable<RootGraph.SignUpRoute> {
        val viewModel: SignUpViewModel = hiltViewModel()
        val state by viewModel.state.collectAsStateWithLifecycle()

        SignUpScreen(
            modifier = modifier,
            screenState = state,
            onIntent = viewModel::handleIntent,
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
    screenState: SignUpScreenState,
    onIntent: (SignUpScreenIntent) -> Unit,
) {
    Column(
        modifier = modifier,
    ) {
        DefaultScreenHeader(
            modifier =
                Modifier
                    .background(color = MaterialTheme.colorScheme.primary),
            origin = stringResource(id = R.string.beginning),
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
                        .padding(horizontal = L).padding(top = XXXL)
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

                SimpleTextField(
                    label = stringResource(id = R.string.name),
                    value = screenState.nameTextFieldState.value,
                    isError = screenState.nameTextFieldState.errorMessage != null,
                    errorMessage =
                        screenState.nameTextFieldState.errorMessage?.let {
                            stringResource(it)
                        },
                    focusManager = focusManager,
                ) {
                    onIntent(SignUpScreenIntent.OnNameChanged(it))
                }

                Spacer(modifier = Modifier.height(L))

                EmailTextField(
                    label = stringResource(id = R.string.email),
                    email = screenState.emailTextFieldState.value,
                    isError = screenState.emailTextFieldState.errorMessage != null,
                    errorMessage =
                        screenState.emailTextFieldState.errorMessage?.let {
                            stringResource(it)
                        },
                    focusManager = focusManager,
                ) {
                    onIntent(SignUpScreenIntent.OnEmailChanged(it))
                }

                Spacer(modifier = Modifier.height(L))

                PasswordTextField(
                    label = stringResource(id = R.string.password),
                    password = screenState.passwordTextFieldState.value,
                    isVisible = screenState.passwordTextFieldState.isVisible,
                    isError = screenState.passwordTextFieldState.errorMessage != null,
                    errorMessage =
                        screenState.passwordTextFieldState.errorMessage?.let {
                            stringResource(it)
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

                CustomButton(
                    modifier = Modifier.fillMaxWidth(),
                    isLightColor = false,
                    color = MaterialTheme.colorScheme.primary,
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
