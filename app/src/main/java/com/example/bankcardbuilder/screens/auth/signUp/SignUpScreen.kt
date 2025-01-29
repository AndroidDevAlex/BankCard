package com.example.bankcardbuilder.screens.auth.signUp

import android.util.Patterns
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bankcardbuilder.R
import com.example.bankcardbuilder.exeption.AccountAlreadyExistsException
import com.example.bankcardbuilder.exeption.EmptyFieldException
import com.example.bankcardbuilder.exeption.InvalidEmailException
import com.example.bankcardbuilder.exeption.InvalidPasswordException
import com.example.bankcardbuilder.exeption.PasswordMismatchException
import com.example.bankcardbuilder.exeption.StorageException
import com.example.bankcardbuilder.screens.CustomError
import com.example.bankcardbuilder.screens.UiStateHandler
import com.example.bankcardbuilder.util.Dimens

@Composable
fun SignUpScreen(
    goToProfileScreen: () -> Unit
) {
    val viewModel = hiltViewModel<SignUpViewModel>()
    val screenState by viewModel.screenState.collectAsState()

    SignUpScreenUi(
        screenState = screenState,
        goToProfileScreen = { goToProfileScreen() },
        onEmailChange = { viewModel.updateScreenState(screenState.copy(email = it)) },
        onPasswordChange = { viewModel.updateScreenState(screenState.copy(password = it)) },
        onConfirmPasswordChange = { viewModel.updateScreenState(screenState.copy(confirmPassword = it)) },
        onTogglePasswordVisibility = {
            viewModel.updateScreenState(screenState.copy(passwordVisible = !screenState.passwordVisible))
        },
        onToggleConfirmPasswordVisibility = {
            viewModel.updateScreenState(screenState.copy(confirmPasswordVisible = !screenState.confirmPasswordVisible))
        },
        onSignUpClick = { viewModel.signUp() }
    )
}

@Composable
private fun SignUpScreenUi(
    screenState: SignUpScreenState,
    goToProfileScreen: () -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onTogglePasswordVisibility: () -> Unit,
    onToggleConfirmPasswordVisibility: () -> Unit,
    onSignUpClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Dimens.PaddingColumn)
            .background(Color.White)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = Dimens.PaddingColumnMod, start = Dimens.PaddingBot)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.fox_image),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(Dimens.IconSizeMod),
                tint = Color.Unspecified
            )

            Spacer(modifier = Modifier.height(Dimens.Spacer))

            Text(
                text = stringResource(R.string.sign_up),
                style = MaterialTheme.typography.headlineLarge.copy(fontSize = Dimens.FontSize),
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(Dimens.SpacerHeight))


        TextField(
            value = screenState.email,
            onValueChange = onEmailChange,
            label = { Text(text = stringResource(R.string.email_address)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = Dimens.BoxPaddingVertical),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(id = R.color.orange),
                unfocusedBorderColor = Color.Gray,
                focusedLabelColor = colorResource(id = R.color.orange),
                unfocusedLabelColor = Color.Gray,
                cursorColor = colorResource(id = R.color.orange)
            ),
            textStyle = TextStyle(
                color = MaterialTheme.colorScheme.onSurface
            ),
            trailingIcon = {
                if (screenState.email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(screenState.email)
                        .matches()
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Clear",
                        tint = colorResource(id = R.color.orange),
                        modifier = Modifier
                            .size(Dimens.IconSizeDp)
                    )
                }
            }
        )
        TextField(
            value = screenState.password,
            onValueChange = onPasswordChange,
            label = { Text(stringResource(R.string.password)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = Dimens.BoxPaddingVertical),
            singleLine = true,
            visualTransformation = if (screenState.passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),

            trailingIcon = {
                val icon =
                    if (screenState.passwordVisible) R.drawable.visibility else R.drawable.visibility_off
                IconButton(onClick = onTogglePasswordVisibility) {

                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = "Toggle Password Visibility"
                    )
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(id = R.color.orange),
                unfocusedBorderColor = Color.Gray,
                focusedLabelColor = colorResource(id = R.color.orange),
                unfocusedLabelColor = Color.Gray,
                cursorColor = colorResource(id = R.color.orange)
            ),
            textStyle = TextStyle(
                color = MaterialTheme.colorScheme.onSurface
            )
        )
        TextField(
            value = screenState.confirmPassword,
            onValueChange = onConfirmPasswordChange,
            label = { Text(stringResource(R.string.confirm_password)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = Dimens.BoxPaddingVertical),
            singleLine = true,
            visualTransformation = if (screenState.confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val icon =
                    if (screenState.confirmPasswordVisible) R.drawable.visibility else R.drawable.visibility_off
                IconButton(onClick = onToggleConfirmPasswordVisibility) {
                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = "Toggle Password Visibility"
                    )
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(id = R.color.orange),
                unfocusedBorderColor = Color.Gray,
                focusedLabelColor = colorResource(id = R.color.orange),
                unfocusedLabelColor = Color.Gray,
                cursorColor = colorResource(id = R.color.orange)
            ),
            textStyle = TextStyle(
                color = MaterialTheme.colorScheme.onSurface
            )
        )

        Spacer(modifier = Modifier.height(Dimens.SpacerHeightMod))

        UiStateHandler(
            isSuccess = screenState.stateUI is SignUpUIState.Success,
            isLoading = screenState.stateUI is SignUpUIState.Loading,
            isError = (screenState.stateUI as? SignUpUIState.Error)?.exception,
            isEmpty = screenState.stateUI is SignUpUIState.Empty,
            onError = { exception ->
                val errorMessage = when (exception) {
                    is EmptyFieldException -> stringResource(
                        R.string.field_is_empty,
                        exception.field.displayName()
                    )
                    is PasswordMismatchException -> stringResource(R.string.passwords_do_not_match)
                    is AccountAlreadyExistsException -> stringResource(R.string.account_already_exists)
                    is InvalidEmailException -> stringResource(R.string.invalid_email)
                    is StorageException -> stringResource(R.string.there_was_an_error_create_account)
                    is InvalidPasswordException -> stringResource(R.string.invalid_password)
                    else -> stringResource(R.string.an_unknown_error_occurred)
                }
                CustomError(errorMessage)
            },
            successMessage = stringResource(R.string.registration_successful),
            onSuccess = { goToProfileScreen() },
            onEmpty = {}
        )
        Button(
            onClick = {
                onSignUpClick()
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = Dimens.PaddingTopBut)
                .width(Dimens.ButtonWidth)
                .height(Dimens.ButtonHeight),
            shape = RoundedCornerShape(Dimens.CornerShape),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.orange),
                contentColor = Color.White
            )
        ) {
            Text(text = stringResource(R.string.sign_up), style = MaterialTheme.typography.titleLarge)
        }
    }
}