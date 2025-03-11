package com.example.bankcardbuilder.features.presentation.settingsCard.cardSettings.uiViews

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.bankcardbuilder.features.presentation.settingsCard.PinCodeCircles
import com.example.bankcardbuilder.R
import com.example.bankcardbuilder.core.domain.CardNumberAlreadyExistsException
import com.example.bankcardbuilder.core.presentstion.components.TopBarCustom
import com.example.bankcardbuilder.core.domain.InvalidFieldFormatException
import com.example.bankcardbuilder.core.domain.StorageException
import com.example.bankcardbuilder.core.presentstion.components.CustomErrorSnackbar
import com.example.bankcardbuilder.features.presentation.settingsCard.PinCodeKeyboard
import com.example.bankcardbuilder.features.presentation.settingsCard.cardSettings.CardSettingsState
import com.example.bankcardbuilder.features.presentation.settingsCard.cardSettings.CardSettingsUIState
import com.example.bankcardbuilder.core.util.Dimens
import com.example.bankcardbuilder.features.presentation.settingsCard.cardSettings.CardSettingsAction

@Composable
fun SetPinCodeScreenUi(
    screenState: CardSettingsState,
    actions: (CardSettingsAction) -> Unit,
    context: Context
) {
    val snackbar = remember { SnackbarHostState() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        TopBarCustom(
            title = stringResource(R.string.set_pin_code),
            onBackClicked = {
                actions(CardSettingsAction.OnBackClick)
            },
            spacerWidth = Dimens.SpacerWidth45
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = Dimens.PaddingTop,
                    start = Dimens.PaddingStart,
                    end = Dimens.PaddingEnd,
                    bottom = Dimens.PaddingBottom
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(Dimens.SpacerHeight42))

            Text(
                text = stringResource(R.string.please_set_your_own),
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = Dimens.TextFont),
                color = MaterialTheme.colorScheme.surface
            )
            Text(
                text = stringResource(R.string.pin_code),
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = Dimens.TextFont),
                color = MaterialTheme.colorScheme.surface
            )

            Spacer(modifier = Modifier.height(Dimens.Height))

            Text(
                text = stringResource(R.string.set_pin_code_5_digit),
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = Dimens.TextFontSize13),
                color = MaterialTheme.colorScheme.onTertiary
            )

            Spacer(modifier = Modifier.height(Dimens.SpacerHeight))

            PinCodeCircles(pinCodeLength = screenState.pinCode.length)

            Spacer(modifier = Modifier.height(Dimens.Height))

            PinCodeKeyboard(pinCode = screenState.pinCode, onPinCodeChange = {
                actions(
                    CardSettingsAction.OnPinCodeChange(it)
                )
            })

            Spacer(modifier = Modifier.weight(1f))

            LaunchedEffect(screenState.uiState) {
                when (val state = screenState.uiState) {
                    is CardSettingsUIState.Success -> {
                        actions(CardSettingsAction.GoToMainScreen)
                    }

                    is CardSettingsUIState.Error -> {
                        val message = when (val error = state.exception) {
                            is InvalidFieldFormatException -> context.getString(
                                R.string.pin_code_must_contain_5_digits
                            )

                            is CardNumberAlreadyExistsException -> context.getString(R.string.a_card_with_this_number_already_exists_for_the_user)
                            is StorageException -> context.getString(R.string.there_was_an_error_saving_your_data_please_try_again)
                            else -> error.message
                                ?: context.getString(R.string.an_unknown_error_occurred)
                        }

                        snackbar.showSnackbar(message)
                    }

                    else -> Unit
                }
            }
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.weight(1f))

                Box(contentAlignment = Alignment.BottomCenter) {
                    Button(
                        onClick = {
                            actions(CardSettingsAction.OnNextClick)
                        },
                        modifier = Modifier
                            .padding(top = Dimens.PaddingTopBut)
                            .width(Dimens.WidthBut)
                            .height(Dimens.HeightBut),
                        shape = RoundedCornerShape(Dimens.CornerShape),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.tertiary
                        )
                    ) {
                        Text(
                            text = stringResource(R.string.set),
                            style = MaterialTheme.typography.bodyLarge.copy(fontSize = Dimens.FontSizeText20)
                        )
                    }
                    CustomErrorSnackbar(snackbar = snackbar)
                }
            }
        }
    }
}