package com.example.bankcardbuilder.screens.registration.uiViews

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import com.example.bankcardbuilder.R
import com.example.bankcardbuilder.exeption.AccountAlreadyExistsException
import com.example.bankcardbuilder.exeption.EmptyFieldException
import com.example.bankcardbuilder.exeption.Field
import com.example.bankcardbuilder.exeption.InvalidFieldFormatException
import com.example.bankcardbuilder.exeption.StorageException
import com.example.bankcardbuilder.screens.CustomInfoSnackbar
import com.example.bankcardbuilder.screens.TopBarCustom
import com.example.bankcardbuilder.screens.registration.RegistrationState
import com.example.bankcardbuilder.screens.registration.RegistrationUIState
import com.example.bankcardbuilder.util.Dimens

@Composable
fun SecurityQuestionScreenUi(
    screenState: RegistrationState,
    onAnswerChange: (String) -> Unit,
    onBackClick: () -> Unit,
    onNextClick: () -> Unit,
    goToMainScreen: () -> Unit,
    clearError: () -> Unit,
    context: Context
) {

    val snackbar = remember { SnackbarHostState() }

    LaunchedEffect(screenState.uiState) {
        when (screenState.uiState) {
            is RegistrationUIState.Success -> {
                goToMainScreen()
            }

            is RegistrationUIState.Error -> {
                if (screenState.answer.isNotBlank()) {
                    val message = when ((screenState.uiState).exception) {
                            is AccountAlreadyExistsException -> context.getString(R.string.account_already_exists)
                            else -> null
                        }
                    message?.let {
                            snackbar.showSnackbar(it)
                    }
                    clearError()
                }
            }

            else -> {}
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TopBarCustom(
            title = stringResource(R.string.security_question),
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
            horizontalAlignment = Alignment.Start
        ) {

            Spacer(modifier = Modifier.height(Dimens.Height))

            Text(
                text = stringResource(R.string.what_was_your),
                style = MaterialTheme.typography.titleMedium.copy(fontSize = Dimens.FontSize),
                color = colorResource(id = R.color.orange),
            )
            Text(
                text = stringResource(R.string.first_school_s),
                style = MaterialTheme.typography.titleMedium.copy(fontSize = Dimens.FontSize),
                color = colorResource(id = R.color.orange),
            )
            Text(
                text = stringResource(R.string.name),
                style = MaterialTheme.typography.titleMedium.copy(fontSize = Dimens.FontSize),
                color = colorResource(id = R.color.orange),
            )
            Spacer(modifier = Modifier.height(Dimens.SpacerHeight))
            Text(
                text = stringResource(R.string.please_write_a_short_answer_in_the_field_below),
                style = MaterialTheme.typography.titleMedium.copy(fontSize = Dimens.TextSizeFont),
                color = colorResource(id = R.color.gray),
            )

            Spacer(modifier = Modifier.height(Dimens.SpacerHeight))

            val answerError =
                (screenState.uiState as? RegistrationUIState.Error)?.exception?.let { exception ->
                    when (exception) {
                        is EmptyFieldException -> if (exception.field == Field.ANSWER)
                            stringResource(R.string.field_is__empty)
                        else null

                        is InvalidFieldFormatException -> if (exception.field == Field.ANSWER)
                            stringResource(R.string.field_must_start_with_a_capital_letter)
                        else null

                        is StorageException -> stringResource(R.string.there_was_an_error_saving_your_data_please_try_again)
                        else -> null
                    }
                }

            OutlinedTextField(
                value = screenState.answer,
                onValueChange = onAnswerChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = Dimens.BoxPaddingVertical)
                    .height(Dimens.HeightMod)
                    .background(
                        colorResource(id = R.color.beige),
                        shape = RoundedCornerShape(Dimens.ButCornerShape)
                    ),
                singleLine = false,
                isError = answerError != null,
                placeholder = {
                    Text(
                        text = stringResource(R.string.write_your_answer_here),
                        color = colorResource(id = R.color.orange)
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    cursorColor = colorResource(id = R.color.orange),
                ),
                textStyle = TextStyle(
                    color = MaterialTheme.colorScheme.onSurface
                )
            )

            answerError?.let {
                Text(
                    text = it,
                    color = Color.Red,
                    fontSize = Dimens.TextFontSp,
                    modifier = Modifier.padding(start = Dimens.PaddingBot, top = Dimens.Top)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.weight(1f))

                Box(contentAlignment = Alignment.BottomCenter) {
                    Button(
                        onClick = { onNextClick() },
                        modifier = Modifier
                            .padding(bottom = Dimens.Padding)
                            .width(Dimens.ButtonWidth)
                            .height(Dimens.ButtonHeight),
                        shape = RoundedCornerShape(Dimens.CornerShape),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.orange),
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            text = stringResource(R.string.save),
                            style = MaterialTheme.typography.titleLarge
                        )
                    }

                    CustomInfoSnackbar(snackbar = snackbar)
                }
            }
        }
    }
}