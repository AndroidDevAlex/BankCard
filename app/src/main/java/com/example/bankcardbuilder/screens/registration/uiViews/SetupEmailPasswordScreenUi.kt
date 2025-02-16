package com.example.bankcardbuilder.screens.registration.uiViews

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
import com.example.bankcardbuilder.R
import com.example.bankcardbuilder.exeption.EmptyFieldException
import com.example.bankcardbuilder.exeption.Field
import com.example.bankcardbuilder.exeption.InvalidEmailException
import com.example.bankcardbuilder.exeption.InvalidPasswordException
import com.example.bankcardbuilder.exeption.PasswordMismatchException
import com.example.bankcardbuilder.screens.registration.RegistrationState
import com.example.bankcardbuilder.screens.registration.RegistrationUIState
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

        TextField(
            value = screenState.email,
            onValueChange = onEmailChange,
            label = { Text(text = stringResource(R.string.email_address)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = Dimens.BoxPaddingVertical),
            singleLine = true,
            isError = emailError != null,
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

        emailError?.let {
            Text(
                text = it,
                color = Color.Red,
                fontSize = Dimens.TextFontSp,
                modifier = Modifier.padding(start = Dimens.PaddingBot, top = Dimens.Top)
            )
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
        TextField(
            value = screenState.password,
            onValueChange = onPasswordChange,
            label = { Text(stringResource(R.string.password)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = Dimens.BoxPaddingVertical),
            singleLine = true,
            isError = passwordError != null,
            visualTransformation = if (screenState.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),

            trailingIcon = {
                val icon =
                    if (screenState.isPasswordVisible) R.drawable.visibility else R.drawable.visibility_off
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

        passwordError?.let {
            Text(
                text = it,
                color = Color.Red,
                fontSize = Dimens.TextFontSp,
                modifier = Modifier.padding(start = Dimens.PaddingBot, top = Dimens.Top)
            )
        }

        val confirmPasswordError =
            (screenState.uiState as? RegistrationUIState.Error)?.exception?.let { exception ->
                when (exception) {
                    is PasswordMismatchException -> stringResource(R.string.passwords_do_not_match)
                    else -> null
                }
            }

        TextField(
            value = screenState.confirmPassword,
            onValueChange = onConfirmPasswordChange,
            label = { Text(stringResource(R.string.confirm_password)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = Dimens.BoxPaddingVertical),
            singleLine = true,
            isError = confirmPasswordError != null,
            visualTransformation = if (screenState.isConfirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val icon =
                    if (screenState.isConfirmPasswordVisible) R.drawable.visibility else R.drawable.visibility_off
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

        confirmPasswordError?.let {
            Text(
                text = it,
                color = Color.Red,
                fontSize = Dimens.TextFontSp,
                modifier = Modifier.padding(start = Dimens.PaddingBot, top = Dimens.Top)
            )
        }

        Spacer(modifier = Modifier.height(Dimens.SpacerHeightMod))

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
                containerColor = colorResource(id = R.color.orange),
                contentColor = Color.White
            )
        ) {
            Text(
                text = stringResource(R.string.sign_up),
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}