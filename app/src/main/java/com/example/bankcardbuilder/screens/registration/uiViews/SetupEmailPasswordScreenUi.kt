package com.example.bankcardbuilder.screens.registration.uiViews

import android.util.Patterns
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.bankcardbuilder.R
import com.example.bankcardbuilder.exeption.EmptyFieldException
import com.example.bankcardbuilder.exeption.Field
import com.example.bankcardbuilder.exeption.InvalidEmailException
import com.example.bankcardbuilder.exeption.InvalidPasswordException
import com.example.bankcardbuilder.exeption.PasswordMismatchException
import com.example.bankcardbuilder.screens.CheckIconCircle
import com.example.bankcardbuilder.screens.CustomTextField
import com.example.bankcardbuilder.screens.registration.RegistrationState
import com.example.bankcardbuilder.screens.registration.RegistrationUIState
import com.example.bankcardbuilder.screens.rememberImeState
import com.example.bankcardbuilder.util.Dimens

@Composable
fun SetupEmailPasswordScreenUi(
    screenState: RegistrationState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onTogglePasswordVisibility: () -> Unit,
    onToggleConfirmPasswordVisibility: () -> Unit,
    onNextClick: () -> Unit,
) {

    val imeState = rememberImeState()
    val scrollState = rememberScrollState()
    val logo = if (isSystemInDarkTheme()) R.drawable.fox_image_black else R.drawable.fox_image_white

    LaunchedEffect(key1 = imeState.value) {
        if (imeState.value) {
            scrollState.animateScrollTo(scrollState.maxValue)
        }
    }

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
                text = stringResource(R.string.sign_up),
                style = MaterialTheme.typography.headlineMedium.copy(fontSize = Dimens.FontSize48),
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        Spacer(modifier = Modifier.height(Dimens.SpacerHeight50))

        val emailError =
            (screenState.uiState as? RegistrationUIState.Error)?.exception?.let { exception ->
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
            (screenState.uiState as? RegistrationUIState.Error)?.exception?.let { exception ->
                when (exception) {
                    is EmptyFieldException -> if (exception.field == Field.PASSWORD) stringResource(
                        R.string.field_is_empty,
                        exception.field.displayName()
                    ) else null

                    is InvalidPasswordException -> stringResource(R.string.invalid_password)
                    else -> null
                }
            }

        val confirmPasswordError =
            (screenState.uiState as? RegistrationUIState.Error)?.exception?.let { exception ->
                when (exception) {
                    is PasswordMismatchException -> stringResource(R.string.passwords_do_not_match)
                    else -> null
                }
            }

        CustomTextField(
            value = screenState.email,
            onValueChange = { newValue ->
                if (newValue.length <= 34) {
                    onEmailChange(newValue)
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
            onValueChange = onPasswordChange,
            label = stringResource(R.string.password),
            isError = passwordError != null,
            errorMessage = passwordError,
            isPassword = true,
            isPasswordVisible = screenState.isPasswordVisible,
            onTogglePasswordVisibility = onTogglePasswordVisibility
        )

        Spacer(modifier = Modifier.height(Dimens.SpacerMod))

        CustomTextField(
            value = screenState.confirmPassword,
            onValueChange = onConfirmPasswordChange,
            label = stringResource(R.string.confirm_password),
            isError = confirmPasswordError != null,
            errorMessage = confirmPasswordError,
            isPassword = true,
            isPasswordVisible = screenState.isConfirmPasswordVisible,
            onTogglePasswordVisibility = onToggleConfirmPasswordVisibility
        )

        Spacer(modifier = Modifier.height(Dimens.SpacerHeight18))

        Button(
            onClick = {
                onNextClick()
            },
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
                text = stringResource(R.string.sign_up),
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = Dimens.TextFontSize)
            )
        }
    }
}