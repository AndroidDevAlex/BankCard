package com.example.bankcardbuilder.screens.registration.uiViews

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.bankcardbuilder.R
import com.example.bankcardbuilder.exeption.EmptyFieldException
import com.example.bankcardbuilder.exeption.Field
import com.example.bankcardbuilder.exeption.InvalidFieldFormatException
import com.example.bankcardbuilder.screens.CheckIconCircle
import com.example.bankcardbuilder.screens.CustomTextField
import com.example.bankcardbuilder.screens.TopBarCustom
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        TopBarCustom(
            title = stringResource(R.string.phone_number),
            onBackClicked = { onBackClick() },
            spacerWidth = Dimens.SpacerWidth30
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
            Spacer(modifier = Modifier.height(Dimens.SpacerHeight25))

            Text(
                text = stringResource(R.string.please_add_your),
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = Dimens.TextFont),
                color = MaterialTheme.colorScheme.surface
            )
            Spacer(modifier = Modifier.height(Dimens.SpacerHeight5))
            Text(
                text = stringResource(R.string.mobile_phone_number),
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = Dimens.TextFont),
                color = MaterialTheme.colorScheme.surface
            )

            Spacer(modifier = Modifier.height(Dimens.SpacerHeight70))


            val phoneError =
                (screenState.uiState as? RegistrationUIState.Error)?.exception?.let { exception ->
                    when (exception) {
                        is EmptyFieldException -> if (exception.field == Field.PHONENUMBER)
                            stringResource(R.string.field_is__empty)
                        else null

                        is InvalidFieldFormatException -> if (exception.field == Field.PHONENUMBER) {
                            stringResource(R.string.entered_fewer_than_10_digits)
                        } else null

                        else -> null
                    }
                }

            CustomTextField(
                value = screenState.phoneNumber,
                onValueChange = onPhoneNumberChange,
                label = stringResource(R.string.phone_number_),
                isError = phoneError != null,
                errorMessage = phoneError,
                isPhoneNumber = true,
                trailingIcon = {
                    if (screenState.phoneNumber.length in 10..15) {
                        CheckIconCircle()
                    }
                }
            )

            Spacer(modifier = Modifier.height(Dimens.SpacerHeight18))

            Button(
                onClick = { onNextClick() },
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
                    text = stringResource(R.string.confirm),
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = Dimens.TextFontSize)
                )
            }
        }
    }
}