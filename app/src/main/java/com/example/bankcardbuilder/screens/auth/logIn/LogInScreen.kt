package com.example.bankcardbuilder.screens.auth.logIn

import android.content.Context
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bankcardbuilder.R
import com.example.bankcardbuilder.exeption.AuthException
import com.example.bankcardbuilder.exeption.EmptyFieldException
import com.example.bankcardbuilder.exeption.Field
import com.example.bankcardbuilder.exeption.InvalidEmailException
import com.example.bankcardbuilder.util.Dimens

@Composable
fun LogInScreen(
    goToMainScreen: () -> Unit,
    goToSignUp: () -> Unit
) {
    val viewModel = hiltViewModel<LogInViewModel>()
    val screenState by viewModel.screenState.collectAsState()
    val context = LocalContext.current

    LogInScreenUi(
        screenState = screenState,
        goToMainScreen = { goToMainScreen() },
        goToSignUp = { goToSignUp() },
        onEmailChange = { viewModel.updateScreenState(screenState.copy(email = it)) },
        onPasswordChange = { viewModel.updateScreenState(screenState.copy(password = it)) },
        onTogglePasswordVisibility = {
            viewModel.updateScreenState(screenState.copy(passwordVisible = !screenState.passwordVisible))
        },
        onSignInClick = { viewModel.signIn() },
        context = context
    )
}

@Composable
private fun LogInScreenUi(
    screenState: LogInScreenState,
    goToMainScreen: () -> Unit,
    goToSignUp: () -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onTogglePasswordVisibility: () -> Unit,
    onSignInClick: () -> Unit,
    context: Context
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
                modifier = Modifier.size(Dimens.IconSizeMod),
                tint = Color.Unspecified
            )

            Spacer(modifier = Modifier.height(Dimens.Spacer))

            Text(
                text = stringResource(R.string.log_in),
                style = MaterialTheme.typography.headlineLarge.copy(fontSize = Dimens.FontSize),
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(Dimens.SpacerHeight))

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
            textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
            trailingIcon = {
                if (Patterns.EMAIL_ADDRESS.matcher(screenState.email)
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

        TextField(
            value = screenState.password,
            onValueChange = onPasswordChange,
            label = { Text(stringResource(R.string.password)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = Dimens.BoxPaddingVertical),
            singleLine = true,

            visualTransformation = if (screenState.passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            isError = passwordError != null,
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
        passwordError?.let {
            Text(
                text = it,
                color = Color.Red,
                fontSize = Dimens.TextFontSp,
                modifier = Modifier.padding(start = Dimens.PaddingBot, top = Dimens.Top)
            )
        }

        Spacer(modifier = Modifier.height(Dimens.SpacerMod))

        Button(
            onClick = { onSignInClick() },
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
                text = stringResource(R.string.log_in),
                style = MaterialTheme.typography.titleLarge
            )
        }

        LaunchedEffect(screenState.stateUI) {
            when (screenState.stateUI) {
                is LogInUIState.Success -> {
                    goToMainScreen()
                }

                else -> {
                    generalError?.let {
                        Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(Dimens.SpacerModHeight))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                text = stringResource(R.string.don_t_have_an_account),
                color = Color.Gray,
                modifier = Modifier.align(Alignment.CenterVertically)
            )

            Text(
                text = stringResource(R.string.sign_up),
                color = colorResource(id = R.color.signUp),
                style = MaterialTheme.typography.headlineMedium.copy(fontSize = Dimens.TextSize),
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .clickable { goToSignUp() }
            )
        }
    }
}