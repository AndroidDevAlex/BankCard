package com.example.bankcardbuilder.screens.registration.uiViews

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import com.example.bankcardbuilder.R
import com.example.bankcardbuilder.exeption.EmptyFieldException
import com.example.bankcardbuilder.exeption.Field
import com.example.bankcardbuilder.exeption.InvalidFieldFormatException
import com.example.bankcardbuilder.screens.TopBarCustom
import com.example.bankcardbuilder.screens.registration.PhoneNumberVisualTransformation
import com.example.bankcardbuilder.screens.registration.RegistrationState
import com.example.bankcardbuilder.screens.registration.RegistrationUIState
import com.example.bankcardbuilder.util.Dimens


@Composable
fun PhoneNumberScreenUi(
    screenState: RegistrationState,
    onPhoneNumberChange: (String) -> Unit,
    onBackClick: () -> Unit,
    onNextClick: () -> Unit
) {

    Column(modifier = Modifier.fillMaxSize()) {
        TopBarCustom(
            title = stringResource(R.string.phone_number),
            onBackClicked = { onBackClick() }
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


            val phoneError =
                (screenState.uiState as? RegistrationUIState.Error)?.exception?.let { exception ->
                    when (exception) {
                        is EmptyFieldException -> if (exception.field == Field.PHONENUMBER)
                            stringResource(R.string.field_is__empty)
                        else null

                        is InvalidFieldFormatException -> if (exception.field == Field.PHONENUMBER) {
                            stringResource(R.string.the_field_must_contain_at_least_10_digits)
                        } else null

                        else -> null
                    }
                }

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
                isError = phoneError != null,
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
                            contentDescription = "Check Icon",
                            tint = colorResource(id = R.color.orange),
                            modifier = Modifier.size(Dimens.IconSizeDp)
                        )
                    }
                }
            )

            phoneError?.let {
                Text(
                    text = it,
                    color = Color.Red,
                    fontSize = Dimens.TextFontSp,
                    modifier = Modifier.padding(start = Dimens.PaddingBot, top = Dimens.Top)
                )
            }
            Spacer(modifier = Modifier.height(Dimens.SpacerHeight34))

            Button(
                onClick = { onNextClick() },
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
                    text = stringResource(R.string.confirm),
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}