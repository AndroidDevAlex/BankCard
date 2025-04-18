package com.example.bankcardbuilder.features.presentation.settingsCard.cardSettings.uiViews

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.bankcardbuilder.R
import com.example.bankcardbuilder.core.domain.EmptyFieldException
import com.example.bankcardbuilder.core.domain.Field
import com.example.bankcardbuilder.core.domain.InvalidFieldFormatException
import com.example.bankcardbuilder.core.presentstion.components.CustomErrorSnackbar
import com.example.bankcardbuilder.core.presentstion.components.TopBarCustom
import com.example.bankcardbuilder.features.presentation.settingsCard.cardSettings.components.BankCard
import com.example.bankcardbuilder.features.presentation.settingsCard.cardSettings.components.CardSettingsDialog
import com.example.bankcardbuilder.features.presentation.settingsCard.cardSettings.CardSettingsState
import com.example.bankcardbuilder.features.presentation.settingsCard.cardSettings.CardSettingsUIState
import com.example.bankcardbuilder.features.presentation.settingsCard.cardSettings.components.InputType
import com.example.bankcardbuilder.ui.theme.Black
import com.example.bankcardbuilder.ui.theme.Blue
import com.example.bankcardbuilder.ui.theme.BrightBlue
import com.example.bankcardbuilder.ui.theme.BrightOrange
import com.example.bankcardbuilder.ui.theme.DarkBlue
import com.example.bankcardbuilder.ui.theme.DarkGray
import com.example.bankcardbuilder.ui.theme.Green
import com.example.bankcardbuilder.ui.theme.LightBlue
import com.example.bankcardbuilder.ui.theme.LightGray
import com.example.bankcardbuilder.ui.theme.LightTurquoise
import com.example.bankcardbuilder.ui.theme.OrangeYellow
import com.example.bankcardbuilder.ui.theme.Red
import com.example.bankcardbuilder.ui.theme.VeryDarkBlue
import com.example.bankcardbuilder.ui.theme.Violet
import com.example.bankcardbuilder.ui.theme.White
import com.example.bankcardbuilder.ui.theme.Yellow
import com.example.bankcardbuilder.core.util.Dimens
import com.example.bankcardbuilder.features.presentation.settingsCard.cardSettings.CardSettingsAction


@Composable
fun CardDetailsScreenUi(
    screenState: CardSettingsState,
    actions: (CardSettingsAction) -> Unit
) {

    val snackbar = remember { SnackbarHostState() }

    var isUserNameDialogOpen by remember { mutableStateOf(false) }
    var isCardNumberDialogOpen by remember { mutableStateOf(false) }
    var isCardPaySystemDialogOpen by remember { mutableStateOf(false) }
    var isExpiryDateDialogOpen by remember { mutableStateOf(false) }

    val colors = listOf(
        LightTurquoise, Green, Blue, OrangeYellow,
        BrightOrange, BrightBlue, Red, Yellow,
        LightGray, DarkGray, Black, DarkBlue,
        VeryDarkBlue, Violet, LightBlue, White
    )

    val textColor = when (screenState.selectedColor) {
        Blue, Red, DarkGray, Black, DarkBlue,
        VeryDarkBlue, Violet, BrightOrange -> White

        else -> Black
    }

    val cardNameError =
        (screenState.uiState as? CardSettingsUIState.Error)?.exception?.let { exception ->
            when (exception) {
                is InvalidFieldFormatException -> if (exception.field == Field.NAME)
                    stringResource(R.string.enter_the_user_s_name) else null

                else -> null
            }
        }

    val cardNumberError =
        (screenState.uiState as? CardSettingsUIState.Error)?.exception?.let { exception ->
            when (exception) {
                is InvalidFieldFormatException -> if (exception.field == Field.CARDNUMBER)
                    stringResource(R.string.less_than_16_digits_entered) else null

                is EmptyFieldException -> if (exception.field == Field.CARDNUMBER)
                    stringResource(R.string.enter_the_card_number) else null

                else -> {}
            }
        }

    val expiryDateError =
        (screenState.uiState as? CardSettingsUIState.Error)?.exception?.let { exception ->
            when (exception) {
                is InvalidFieldFormatException -> if (exception.field == Field.EXPIRYDATE)
                    stringResource(R.string.less_than_4_digits_entered) else null

                is EmptyFieldException -> if (exception.field == Field.EXPIRYDATE)
                    stringResource(R.string.enter_the_card_s_expiration_date) else null

                else -> {}
            }
        }

    val cardPaySystemError =
        (screenState.uiState as? CardSettingsUIState.Error)?.exception?.let { exception ->
            when (exception) {
                is EmptyFieldException -> if (exception.field == Field.CARDPAYSYSTEM)
                    stringResource(R.string.enter_the_pay_system_name) else null

                else -> null
            }
        }

    LaunchedEffect(screenState.uiState) {
        when (screenState.uiState) {
            is CardSettingsUIState.Success -> {
                actions(CardSettingsAction.GoToPinCodeScreen)
            }

            is CardSettingsUIState.Error -> {
                val errorMessage = listOfNotNull(
                    cardNameError,
                    cardNumberError,
                    expiryDateError,
                    cardPaySystemError
                ).joinToString("\n")

                if (errorMessage.isNotEmpty()) {
                    snackbar.showSnackbar(errorMessage)

                }
            }

            else -> Unit
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .padding(bottom = Dimens.PaddingBottom30)
    ) {
        TopBarCustom(
            title = stringResource(R.string.card_color),
            onBackClicked = {
                actions(CardSettingsAction.GoToMainScreen)
            },
            spacerWidth = Dimens.SpacerWidth50
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = Dimens.PaddingTop6,
                    start = Dimens.PaddingStart35,
                    end = Dimens.PaddingEnd35,
                    bottom = Dimens.PaddingBottom20
                ),
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(Dimens.SpacerHeight42))

                Text(
                    text = stringResource(R.string.here_you_can_fill_out_and_customize_the_design_of_your_credit_card),
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = Dimens.FontSize15),
                    color = MaterialTheme.colorScheme.onTertiary
                )

            }
            Spacer(modifier = Modifier.height(Dimens.SpacerHeight38))

            BankCard(
                color = screenState.selectedColor,
                cardName = screenState.userName,
                cardNumber = screenState.cardNumber,
                expiryDate = screenState.expiryDate,
                cardPaySystem = screenState.cardPaySystem,
                textColor = textColor,
                onCardNameClick = { isUserNameDialogOpen = true },
                onCardNumberClick = { isCardNumberDialogOpen = true },
                onCardPaySystemClick = { isCardPaySystemDialogOpen = true },
                onExpiryDateClick = { isExpiryDateDialogOpen = true },
                modifier = Modifier
                    .fillMaxWidth()
            )


            if (isUserNameDialogOpen) {
                CardSettingsDialog(
                    title = stringResource(R.string.edit_user_name),
                    onValueChanged = { newValue ->
                        actions(CardSettingsAction.OnUserNameChanged(newValue.ifBlank { "Your Name" }))
                    },

                    onDismiss = {
                        isUserNameDialogOpen = false
                    },
                    inputType = InputType.NAME
                )
            }

            if (isCardNumberDialogOpen) {
                CardSettingsDialog(
                    title = stringResource(R.string.edit_card_number),
                    onValueChanged = { newValue ->
                        actions(CardSettingsAction.OnCardNumberChanged(newValue))
                    },
                    onDismiss = {
                        isCardNumberDialogOpen = false
                    },
                    inputType = InputType.NUMBER
                )
            }

            if (isCardPaySystemDialogOpen) {
                CardSettingsDialog(
                    title = stringResource(R.string.edit_card_paySystem),
                    onValueChanged = { newValue ->
                        actions(CardSettingsAction.OnCardPaySystemChanged(newValue.ifBlank { "PaySystem" }))
                    },
                    onDismiss = { isCardPaySystemDialogOpen = false },
                    inputType = InputType.PAY_SYSTEM
                )
            }

            if (isExpiryDateDialogOpen) {
                CardSettingsDialog(
                    title = stringResource(R.string.edit_expiry_date),
                    onValueChanged = { newValue ->
                        actions(CardSettingsAction.OnExpiryDateChanged(newValue.ifBlank { "MM/YY" }))
                    },
                    onDismiss = { isExpiryDateDialogOpen = false },
                    inputType = InputType.DATE
                )
            }

            Spacer(modifier = Modifier.height(Dimens.SpacerHeight38))

            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                horizontalArrangement = Arrangement.spacedBy(Dimens.HorizontalArrSpacedBy),
                verticalArrangement = Arrangement.spacedBy(Dimens.SpacedBy16),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = Dimens.PaddingStart, end = Dimens.PaddingEnd)
            ) {
                items(colors) { color ->
                    ColorCircle(
                        color = color,
                        iconColor = textColor,
                        isSelected = color == screenState.selectedColor,
                        onClick = {
                            actions(CardSettingsAction.OnColorSelected(color))
                        }
                    )
                }
            }

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.weight(0.1f))

                Box(
                    contentAlignment = Alignment.BottomCenter,
                ) {

                    Button(
                        onClick = {
                            actions(CardSettingsAction.OnNextClick)
                        },
                        modifier = Modifier
                            .padding(top = Dimens.PaddingTopBut)
                            .width(Dimens.WidthBut)
                            .height(Dimens.HeightButMod),
                        shape = RoundedCornerShape(Dimens.RoundedCornerShape),
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
                    CustomErrorSnackbar(snackbar = snackbar)
                }

            }
        }
    }
}

@Composable
private fun ColorCircle(
    color: Color,
    iconColor: Color,
    isSelected: Boolean,
    onClick: () -> Unit
) {

    val isDarkTheme = isSystemInDarkTheme()
    val shouldHaveWhiteBorder =
        isDarkTheme && (color == DarkGray || color == Black || color == VeryDarkBlue)

    Box(
        modifier = Modifier
            .padding(Dimens.Padding1)
            .shadow(
                elevation = if (color == Color.White) Dimens.BoxShadow2 else Dimens.BoxShadow0,
                shape = CircleShape
            )
            .then(
                if (shouldHaveWhiteBorder) Modifier.border(Dimens.Border, Color.White, CircleShape)
                else Modifier
            )
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .clip(CircleShape)
                .background(color)
                .clickable { onClick() }
                .aspectRatio(1f)
        ) {
            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Selected",
                    tint = iconColor,
                    modifier = Modifier.size(Dimens.IconSize33)
                )
            }
        }
    }
}