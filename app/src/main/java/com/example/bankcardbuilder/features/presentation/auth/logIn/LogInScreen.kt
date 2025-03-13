package com.example.bankcardbuilder.features.presentation.auth.logIn

import android.content.Context
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bankcardbuilder.R
import com.example.bankcardbuilder.core.domain.AuthException
import com.example.bankcardbuilder.core.domain.EmptyFieldException
import com.example.bankcardbuilder.core.domain.Field
import com.example.bankcardbuilder.core.domain.InvalidEmailException
import com.example.bankcardbuilder.core.presentstion.components.CheckIconCircle
import com.example.bankcardbuilder.core.presentstion.components.CustomTextField
import com.example.bankcardbuilder.core.presentstion.components.rememberImeState
import com.example.bankcardbuilder.core.util.Dimens

@Composable
fun LogInScreen(
    goToMainScreen: () -> Unit,
    goToSignUp: () -> Unit
) {
    val viewModel = hiltViewModel<LogInViewModel>()
    val screenState by viewModel.screenState.collectAsState()
    val context = LocalContext.current

    val imeState = rememberImeState()
    val scrollState = rememberScrollState()

    LaunchedEffect(key1 = imeState.value) {
        if (imeState.value) {
            scrollState.animateScrollTo(scrollState.maxValue)
        }
    }

    LogInScreenUi(
        screenState = screenState,
        actions = { action ->
            when (action) {
                is LogInAction.GoToMainScreen -> goToMainScreen()
                is LogInAction.GoToSignUp -> goToSignUp()
                else -> Unit
            }
            viewModel.onAction(action)
        },
        context = context,
        scrollState = scrollState
    )
}

@Composable
private fun LogInScreenUi(
    screenState: LogInScreenState,
    actions: (LogInAction) -> Unit,
    context: Context,
    scrollState: ScrollState
) {

    val logo = if (isSystemInDarkTheme()) R.drawable.fox_image_black else R.drawable.fox_image_white
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(Dimens.ColumnPadding)
            .background(MaterialTheme.colorScheme.background)
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = Dimens.PaddingColumnTop)
        ) {
            Icon(
                painter = painterResource(id = logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(Dimens.IconSize60)
                    .padding(start = Dimens.PaddingStartIcon),
                tint = Color.Unspecified
            )

            Spacer(modifier = Modifier.height(Dimens.Spacer))

            Text(
                text = stringResource(R.string.log_in),
                style = MaterialTheme.typography.headlineMedium.copy(fontSize = Dimens.FontSize48),
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Spacer(modifier = Modifier.height(Dimens.SpacerHeight50))

        val generalError =
            (screenState.stateUI as? LogInUIState.Error)?.exception?.let { exception ->
                when (exception) {
                    is AuthException -> stringResource(R.string.this_user_does_not_exist)
                    else -> null
                }
            }

        val emailError = (screenState.stateUI as? LogInUIState.Error)?.exception?.let { exception ->
            when (exception) {
                is EmptyFieldException -> if (exception.field == Field.EMAIL) stringResource(
                    R.string.field_is_empty,
                    exception.field.displayName()
                ) else null

                is InvalidEmailException -> stringResource(R.string.invalid_email)
                else -> null
            }
        }

        val passwordError =
            (screenState.stateUI as? LogInUIState.Error)?.exception?.let { exception ->
                when (exception) {
                    is EmptyFieldException -> if (exception.field == Field.PASSWORD) stringResource(
                        R.string.field_is_empty,
                        exception.field.displayName()
                    ) else null

                    else -> null
                }
            }

        CustomTextField(
            value = screenState.email,
            onValueChange = { newValue ->
                if (newValue.length <= 34) {
                    actions(LogInAction.OnEmailChange(newValue))
                }
            },
            label = stringResource(R.string.email_address),
            isError = emailError != null,
            errorMessage = emailError,
            trailingIcon = {
                if (Patterns.EMAIL_ADDRESS.matcher(screenState.email).matches()) {
                    Row {
                        CheckIconCircle()
                    }
                }
            }
        )
        Spacer(modifier = Modifier.height(Dimens.SpacerMod))

        CustomTextField(
            value = screenState.password,
            onValueChange = { newValue ->
                actions(LogInAction.OnPasswordChange(newValue))
            },
            label = stringResource(R.string.password),
            isError = passwordError != null,
            errorMessage = passwordError,
            isPassword = true,
            isPasswordVisible = screenState.passwordVisible,
            onTogglePasswordVisibility = { actions(LogInAction.OnTogglePasswordVisibility) }
        )

        Spacer(modifier = Modifier.height(Dimens.SpacerHeight18))

        Button(
            onClick = { actions(LogInAction.OnSignInClick) },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = Dimens.PaddingTopBut)
                .width(Dimens.ButtonWidth)
                .height(Dimens.ButtonHeight),
            shape = RoundedCornerShape(Dimens.CornerShape),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.tertiary
            )
        ) {
            Text(
                text = stringResource(R.string.log_in),
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = Dimens.TextFontSize)
            )
        }

        LaunchedEffect(screenState.stateUI) {
            when (screenState.stateUI) {
                is LogInUIState.Success -> {
                    actions(LogInAction.GoToMainScreen)
                }

                else -> {
                    generalError?.let {
                        Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(Dimens.SpacerHeight25))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                text = stringResource(R.string.don_t_have_an_account),
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = Dimens.TextFontSp),
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.align(Alignment.CenterVertically)
            )

            Text(
                text = stringResource(R.string.sign_up),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = Dimens.TextFontSizeSp),
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .clickable { actions(LogInAction.GoToSignUp) }
            )
        }
    }
}