package com.example.bankcardbuilder.features.presentation.registration.uiViews

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import com.example.bankcardbuilder.R
import com.example.bankcardbuilder.core.domain.AccountAlreadyExistsException
import com.example.bankcardbuilder.core.domain.EmptyFieldException
import com.example.bankcardbuilder.core.domain.Field
import com.example.bankcardbuilder.core.domain.InvalidFieldException
import com.example.bankcardbuilder.core.domain.InvalidFieldFormatException
import com.example.bankcardbuilder.core.domain.StorageException
import com.example.bankcardbuilder.core.presentstion.components.CustomErrorSnackbar
import com.example.bankcardbuilder.core.presentstion.components.TopBarCustom
import com.example.bankcardbuilder.features.presentation.registration.RegistrationState
import com.example.bankcardbuilder.features.presentation.registration.RegistrationUIState
import com.example.bankcardbuilder.core.presentstion.components.rememberImeState
import com.example.bankcardbuilder.core.util.Dimens
import com.example.bankcardbuilder.features.presentation.registration.RegistrationAction

@Composable
fun SecurityQuestionScreenUi(
    screenState: RegistrationState,
    actions: (RegistrationAction) -> Unit,
    context: Context
) {

    val snackbar = remember { SnackbarHostState() }
    val imeState = rememberImeState()
    val scrollState = rememberScrollState()
    val bottomPadding = if (imeState.value) Dimens.Padding20 else Dimens.Padding0
    val heightSpacer = if (imeState.value) Dimens.HeightSpacer else Dimens.HeightSpacer120

    LaunchedEffect(key1 = imeState.value) {
        if (imeState.value) {
            scrollState.animateScrollTo(scrollState.maxValue)
        }
    }

    LaunchedEffect(screenState.uiState) {
        when (screenState.uiState) {
            is RegistrationUIState.Success -> {
                actions(RegistrationAction.GoToMainScreen)
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
                    actions(RegistrationAction.ClearError)
                }
            }

            else -> {}
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(MaterialTheme.colorScheme.background)
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        TopBarCustom(
            title = stringResource(R.string.security_question),
            onBackClicked = {
                actions(RegistrationAction.OnBackClick)
            },
            spacerWidth = Dimens.SpacerWidth23
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = Dimens.PaddingTop,
                    start = Dimens.PaddingStart,
                    end = Dimens.PaddingEnd,
                ),
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(modifier = Modifier.height(Dimens.SpacerHeight30))

            Text(
                text = stringResource(R.string.what_was_your),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = Dimens.TextFont44,
                    lineHeight = Dimens.LineHeight
                ),
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = stringResource(R.string.first_school_s),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = Dimens.TextFont44,
                    lineHeight = Dimens.LineHeight
                ),
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = stringResource(R.string.name),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = Dimens.TextFont44,
                    lineHeight = Dimens.LineHeight
                ),
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(Dimens.SpacerHeight23))

            Text(
                text = stringResource(R.string.please_write_a_short_answer_in_the_field_below),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = Dimens.TextFontSp,
                    lineHeight = Dimens.LineHeight18
                ),
                color = MaterialTheme.colorScheme.surface
            )

            Spacer(modifier = Modifier.height(Dimens.SpacerHeight38))
            val answerError =
                (screenState.uiState as? RegistrationUIState.Error)?.exception?.let { exception ->
                    when (exception) {
                        is EmptyFieldException -> if (exception.field == Field.ANSWER)
                            stringResource(R.string.field_is__empty)
                        else null

                        is InvalidFieldFormatException -> if (exception.field == Field.ANSWER)
                            stringResource(R.string.answer)
                        else null

                        is InvalidFieldException -> if (exception.field == Field.ANSWER) {
                            stringResource(R.string.only_first_letter)
                        } else null

                        is StorageException -> stringResource(R.string.there_was_an_error_saving_your_data_please_try_again)
                        else -> null
                    }
                }
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {

                OutlinedTextField(
                    value = screenState.answer,
                    onValueChange = {
                        actions(RegistrationAction.OnAnswerChange(it))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(Dimens.HeightMod)
                        .background(
                            MaterialTheme.colorScheme.onPrimary,
                            shape = RoundedCornerShape(Dimens.RoundedCornerShape10)
                        ),
                    singleLine = false,
                    isError = answerError != null,
                    placeholder = {
                        Text(
                            text = stringResource(R.string.write_your_answer_here),
                            style = MaterialTheme.typography.bodyMedium.copy(fontSize = Dimens.TextFont15),
                            color = MaterialTheme.colorScheme.primary
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        cursorColor = MaterialTheme.colorScheme.primary,
                    ),
                    textStyle = TextStyle(
                        MaterialTheme.colorScheme.onSecondary
                    )
                )
            }
            answerError?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = Dimens.TextFont12),
                    modifier = Modifier.padding(
                        start = Dimens.PaddingStart2,
                        top = Dimens.Top
                    )
                )
            }


            Spacer(modifier = Modifier.height(heightSpacer))

            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = bottomPadding),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(heightSpacer))

                    Button(
                        onClick = {
                            actions(RegistrationAction.OnNextClick)
                        },
                        modifier = Modifier
                            .width(Dimens.ButtonWidth)
                            .height(Dimens.ButtonHeight),
                        shape = RoundedCornerShape(Dimens.CornerShape),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.tertiary
                        )
                    ) {
                        Text(
                            text = stringResource(R.string.save),
                            style = MaterialTheme.typography.bodyLarge.copy(fontSize = Dimens.TextFontSize)
                        )
                    }
                }

                CustomErrorSnackbar(
                    snackbar = snackbar,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                )
            }
        }
    }
}