package com.example.bankcardbuilder.screens.settingsAccount.securityQuestion

import androidx.compose.foundation.background
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bankcardbuilder.R
import com.example.bankcardbuilder.screens.TopBarCustom
import com.example.bankcardbuilder.exeption.EmptyFieldException
import com.example.bankcardbuilder.exeption.InvalidFieldFormatException
import com.example.bankcardbuilder.exeption.StorageException
import com.example.bankcardbuilder.screens.CustomError
import com.example.bankcardbuilder.screens.UiStateHandler
import com.example.bankcardbuilder.util.Dimens

@Composable
fun SecurityQuestionScreen(
    goBackToPhoneNumberScreen: () -> Unit,
    goToMainScreen: () -> Unit
) {
    val viewModel = hiltViewModel<SecurityQuestionViewModel>()
    val answer by viewModel.answer.collectAsState()
    val screenState by viewModel.screenState.collectAsState()

    SecurityQuestionScreenUi(
        answer = answer,
        screenState = screenState,
        onAnswerChange = { viewModel.updateAnswer(it) },
        onSave = { viewModel.saveAnswer() },
        goBackToPhoneNumberScreen = goBackToPhoneNumberScreen,
        goToMainScreen = goToMainScreen
    )
}

@Composable
private fun SecurityQuestionScreenUi(
    answer: String,
    screenState: SecurityQuestionScreenState,
    onAnswerChange: (String) -> Unit,
    onSave: () -> Unit,
    goBackToPhoneNumberScreen: () -> Unit,
    goToMainScreen: () -> Unit
) {

    Column(modifier = Modifier.fillMaxSize()) {
        TopBarCustom(
            title = stringResource(R.string.security_question),
            onBackClicked = { goBackToPhoneNumberScreen() }
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

            OutlinedTextField(
                value = answer,
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

            Spacer(modifier = Modifier.weight(1f))

            UiStateHandler(
                isLoading = screenState is SecurityQuestionScreenState.Loading,
                isSuccess = screenState is SecurityQuestionScreenState.Success,
                isError = (screenState as? SecurityQuestionScreenState.Error)?.exception,
                isEmpty = screenState is SecurityQuestionScreenState.Empty,
                onError = { exception ->
                    val errorMessage = when (exception) {
                        is EmptyFieldException -> stringResource(
                            R.string.field_is_empty,
                            exception.field.displayName()
                        )
                        is StorageException -> stringResource(R.string.there_was_an_error_saving_your_data_please_try_again)
                        is InvalidFieldFormatException -> stringResource(
                            R.string.field_must_start_with_a_capital_letter,
                            exception.field.displayName()
                        )
                        else -> stringResource(R.string.an_unknown_error_occurred)
                    }
                    CustomError(errorMessage)
                },
                successMessage = stringResource(R.string.data_saved_successfully),
                onSuccess = { goToMainScreen() },
                onEmpty = {}
            )

            Spacer(modifier = Modifier.height(Dimens.PaddingTop))

            Button(
                onClick = {
                    onSave()
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = Dimens.PaddingBottom)
                    .width(Dimens.ButtonWidth)
                    .height(Dimens.ButtonHeight),
                shape = RoundedCornerShape(Dimens.CornerShape),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.orange),
                    contentColor = Color.White
                )
            ) {
                Text(text = stringResource(R.string.save), style = MaterialTheme.typography.titleLarge)
            }
        }
    }
}