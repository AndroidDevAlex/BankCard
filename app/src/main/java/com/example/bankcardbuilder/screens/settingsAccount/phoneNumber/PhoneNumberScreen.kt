package com.example.bankcardbuilder.screens.settingsAccount.phoneNumber

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bankcardbuilder.R
import com.example.bankcardbuilder.screens.TopBarCustom
import com.example.bankcardbuilder.exeption.AuthException
import com.example.bankcardbuilder.exeption.StorageException
import com.example.bankcardbuilder.screens.CustomError
import com.example.bankcardbuilder.screens.UiStateHandler
import com.example.bankcardbuilder.util.Dimens

@Composable
fun PhoneNumberScreen(
    goBackToProfileScreen: () -> Unit,
    goToSecurityQuestionScreen: () -> Unit
) {

    val viewModel = hiltViewModel<PhoneNumberScreenViewModel>()
    val uiState by viewModel.uiState.collectAsState()


    PhoneNumberScreenUi(
        screenState = uiState,
        goBackToProfileScreen = { goBackToProfileScreen() },
        goToSecurityQuestionScreen = { goToSecurityQuestionScreen() },
        onPhoneNumberChange = { viewModel.updateScreenState(uiState.copy(phoneNumber = it)) },
        onSavePhoneNumberClick = { viewModel.savePhoneNumber() }
    )
}

@Composable
private fun PhoneNumberScreenUi(
    screenState: PhoneNumberScreenState,
    goBackToProfileScreen: () -> Unit,
    goToSecurityQuestionScreen: () -> Unit,
    onPhoneNumberChange: (String) -> Unit,
    onSavePhoneNumberClick: () -> Unit
) {

    Column(modifier = Modifier.fillMaxSize()) {
        TopBarCustom(
            title = stringResource(R.string.phone_number),
            onBackClicked = { goBackToProfileScreen() }
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = Dimens.PaddingTop,
                    start = Dimens.PaddingStart,
                    end = Dimens.PaddingEnd
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(Dimens.Height))

            Text(
                text = stringResource(R.string.please_add_your),
                style = MaterialTheme.typography.titleMedium,
                color = colorResource(id = R.color.gray),
            )

            Text(
                text = stringResource(R.string.mobile_phone_number),
                style = MaterialTheme.typography.titleMedium,
                color = colorResource(id = R.color.gray),
            )

            Spacer(modifier = Modifier.height(Dimens.SpacerHeight55))

            TextField(
                value = screenState.phoneNumber,
                onValueChange = { newValue ->
                    val filteredValue = newValue.filter { it.isDigit() }.take(15)
                    onPhoneNumberChange(filteredValue)
                },
                label = { Text(text = stringResource(R.string.phone_number_)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = Dimens.BoxPaddingVertical),
                visualTransformation = PhoneNumberVisualTransformation(),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorResource(id = R.color.blue),
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = colorResource(id = R.color.orange),
                    unfocusedLabelColor = Color.Gray,
                    cursorColor = colorResource(id = R.color.orange)
                ),
                textStyle = TextStyle(
                    color = MaterialTheme.colorScheme.onSurface
                ),
                trailingIcon = {
                    if (screenState.phoneNumber.length in 10..15) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Clear",
                            tint = colorResource(id = R.color.orange),
                            modifier = Modifier.size(Dimens.IconSizeDp)
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(Dimens.SpacerHeight34))

            UiStateHandler(
                isLoading = screenState.stateUI is PhoneNumberUIState.Loading,
                isSuccess = screenState.stateUI is PhoneNumberUIState.Success,
                isError = (screenState.stateUI as? PhoneNumberUIState.Error)?.exception,
                isEmpty = screenState.stateUI is PhoneNumberUIState.Empty,
                onError = { exception ->
                    val errorMessage = when (exception) {
                        is AuthException -> stringResource(R.string.this_user_does_not_exist)
                        is StorageException -> stringResource(R.string.there_was_an_error_saving_your_data_please_try_again)
                        else -> stringResource(R.string.an_unknown_error_occurred)
                    }
                    CustomError(errorMessage)
                },
                successMessage = stringResource(R.string.phone_number_saved_successfully),
                onSuccess = { goToSecurityQuestionScreen() },
                onEmpty = {}
            )
            Button(
                onClick = {
                    onSavePhoneNumberClick()
                },
                enabled = screenState.phoneNumber.isNotBlank() && screenState.phoneNumber.length in 10..15,
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
                Text(text = stringResource(R.string.confirm), style = MaterialTheme.typography.titleLarge)
            }
        }
    }
}