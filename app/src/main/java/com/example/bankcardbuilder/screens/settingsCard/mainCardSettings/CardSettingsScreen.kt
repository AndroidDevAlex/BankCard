package com.example.bankcardbuilder.screens.settingsCard.mainCardSettings

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bankcardbuilder.R
import com.example.bankcardbuilder.screens.TopBarCustom
import com.example.bankcardbuilder.util.Utils
import com.example.bankcardbuilder.exeption.AuthException
import com.example.bankcardbuilder.exeption.CardNumberAlreadyExistsException
import com.example.bankcardbuilder.exeption.InvalidFieldFormatException
import com.example.bankcardbuilder.exeption.StorageException
import com.example.bankcardbuilder.screens.CustomError
import com.example.bankcardbuilder.screens.UiStateHandler
import com.example.bankcardbuilder.ui.theme.Amethyst
import com.example.bankcardbuilder.ui.theme.Aquamarine
import com.example.bankcardbuilder.ui.theme.Beige
import com.example.bankcardbuilder.ui.theme.DarkGreen
import com.example.bankcardbuilder.ui.theme.OliveGreen
import com.example.bankcardbuilder.ui.theme.Orange
import com.example.bankcardbuilder.ui.theme.Pink
import com.example.bankcardbuilder.ui.theme.SandyBrown
import com.example.bankcardbuilder.util.Dimens

@Composable
fun CardSettingsScreen(
    goToMainScreen: () -> Unit,
    goToPinCodeScreen: (String) -> Unit
) {

    val viewModel = hiltViewModel<CardSettingsViewModel>()
    val screenState by viewModel.screenState.collectAsState()

    CardSettingsScreenUi(
        screenState = screenState,
        onUserNameChanged = { viewModel.updateScreenState(screenState.copy(userName = it)) },
        onCardNumberChanged = { viewModel.updateScreenState(screenState.copy(cardNumber = it)) },
        onExpiryDateChanged = { viewModel.updateScreenState(screenState.copy(expiryDate = it)) },
        onCardCompanyChanged = { viewModel.updateScreenState(screenState.copy(cardCompany = it)) },
        onColorSelected = { viewModel.updateScreenState(screenState.copy(selectedColor = it)) },
        goToMainScreen = { goToMainScreen() },
        goToPinCodeScreen = { goToPinCodeScreen(screenState.cardNumber) },
        onSaveCardData = { viewModel.saveCardData() }
    )
}

@Composable
private fun CardSettingsScreenUi(
    screenState: CardSettingsScreenState,
    onUserNameChanged: (String) -> Unit,
    onCardNumberChanged: (String) -> Unit,
    onExpiryDateChanged: (String) -> Unit,
    onCardCompanyChanged: (String) -> Unit,
    onColorSelected: (Color) -> Unit,
    goToMainScreen: () -> Unit,
    goToPinCodeScreen: () -> Unit,
    onSaveCardData: () -> Unit

) {

    var isUserNameDialogOpen by remember { mutableStateOf(false) }
    var isCardNumberDialogOpen by remember { mutableStateOf(false) }
    var isCardCompanyDialogOpen by remember { mutableStateOf(false) }
    var isExpiryDateDialogOpen by remember { mutableStateOf(false) }

    val colors = listOf(
        Color.LightGray, Color.Red, Color.Blue, Color.Green, Color.Yellow, Color.Magenta,
        Color.Cyan, Color.White, Pink, Orange, DarkGreen, Beige, Aquamarine,
        OliveGreen, SandyBrown, Amethyst
    )

    val textColor = when (screenState.selectedColor) {
        Color.LightGray, Color.Green, Color.Yellow, Color.Cyan, Color.White, Beige, Aquamarine -> Color.Black
        else -> Color.White
    }
    Column(modifier = Modifier.fillMaxSize()) {
        TopBarCustom(
            title = stringResource(R.string.card_settings),
            onBackClicked = { goToMainScreen() }
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = Dimens.PaddingTop,
                    start = Dimens.PaddingStart,
                    end = Dimens.PaddingEnd,
                    bottom = Dimens.PaddingBot
                ),
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(Dimens.Height))

                Text(
                    text = stringResource(R.string.here_you_can_fill_out_and_customize_the_design_of_your_credit_card),
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = Dimens.ColumnTextFontSize),
                    color = colorResource(id = R.color.dark_gray),
                    fontWeight = FontWeight.Normal,
                )

            }
            Spacer(modifier = Modifier.height(Dimens.Height))

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                BankCard(
                    color = screenState.selectedColor,
                    cardName = screenState.userName,
                    cardNumber = screenState.cardNumber,
                    expiryDate = screenState.expiryDate,
                    cardCompany = screenState.cardCompany,
                    textColor = textColor,
                    onCardNameClick = {
                        isUserNameDialogOpen = true
                    },
                    onCardNumberClick = {
                        isCardNumberDialogOpen = true
                    },
                    onCardCompanyClick = {
                        isCardCompanyDialogOpen = true
                    },
                    onExpiryDateClick = {
                        isExpiryDateDialogOpen = true
                    }
                )
            }

            if (isUserNameDialogOpen) {
                CardSettingsDialog(
                    title = stringResource(R.string.edit_user_name),
                    value = screenState.userName,
                    onValueChanged = { newValue -> onUserNameChanged(newValue.ifBlank { "**** ****" }) },

                    onDismiss = {
                        isUserNameDialogOpen = false
                    },
                    inputType = InputType.NAME
                )
            }

            if (isCardNumberDialogOpen) {
                CardSettingsDialog(
                    title = stringResource(R.string.edit_card_number),
                    value = screenState.cardNumber,
                    onValueChanged = { newValue -> onCardNumberChanged(newValue) },
                    onDismiss = {
                        isCardNumberDialogOpen = false
                    },
                    inputType = InputType.NUMBER
                )
            }

            if (isCardCompanyDialogOpen) {
                CardSettingsDialog(
                    title = stringResource(R.string.edit_card_company),
                    value = screenState.cardCompany,
                    onValueChanged = { newValue -> onCardCompanyChanged(newValue.ifBlank { "****" }) },
                    onDismiss = { isCardCompanyDialogOpen = false },
                    inputType = InputType.COMPANY
                )
            }

            if (isExpiryDateDialogOpen) {
                CardSettingsDialog(
                    title = stringResource(R.string.edit_expiry_date),
                    value = screenState.expiryDate,
                    onValueChanged = { newValue -> onExpiryDateChanged(newValue) },
                    onDismiss = { isExpiryDateDialogOpen = false },
                    inputType = InputType.DATE
                )
            }

            Spacer(modifier = Modifier.height(Dimens.SpacerModHeight))

            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                horizontalArrangement = Arrangement.spacedBy(Dimens.HorizontalArrSpacedBy),
                verticalArrangement = Arrangement.spacedBy(Dimens.VerticalArrSpacedBy),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = Dimens.PaddingStart, end = Dimens.PaddingEnd)
            ) {
                items(colors) { color ->
                    ColorCircle(color = color, isSelected = color == screenState.selectedColor) {
                        onColorSelected(color)
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            UiStateHandler(
                isLoading = screenState.uiState is CardSettingsUIState.Loading,
                isSuccess = screenState.uiState is CardSettingsUIState.Success,
                isError = (screenState.uiState as? CardSettingsUIState.Error)?.exception,
                isEmpty = screenState.uiState is CardSettingsUIState.Empty,
                onError = { exception ->
                    val errorMessage = when (exception) {
                        is InvalidFieldFormatException -> stringResource(
                            R.string.field_must_not_contain,
                            exception.field.displayName()
                        )
                        is StorageException -> stringResource(R.string.there_was_an_error_saving_your_data_please_try_again)
                        is CardNumberAlreadyExistsException -> stringResource(R.string.a_card_with_this_number_already_exists_for_the_user)
                        is AuthException -> stringResource(R.string.this_user_does_not_exist)
                        else -> stringResource(R.string.an_unknown_error_occurred)
                    }
                    CustomError(errorMessage)
                },
                successMessage = stringResource(R.string.data_saved_successfully),
                onSuccess = {
                    goToPinCodeScreen()
                },

                onEmpty = {}
            )

            Button(
                onClick = {
                    onSaveCardData()
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = Dimens.PaddingTopBut)
                    .width(Dimens.WidthBut)
                    .height(Dimens.HeightButMod),
                shape = RoundedCornerShape(Dimens.RoundedCornerShape),
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

@Composable
private fun BankCard(
    color: Color,
    cardName: String,
    cardNumber: String,
    expiryDate: String,
    cardCompany: String,
    textColor: Color,
    onCardNameClick: () -> Unit,
    onCardNumberClick: () -> Unit,
    onCardCompanyClick: () -> Unit,
    onExpiryDateClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(Dimens.BoxSizeWidth, Dimens.BoxSizeHeight)
            .background(color, shape = RoundedCornerShape(Dimens.CornerShape))
            .border(Dimens.BoxBorder, Color.Black, shape = RoundedCornerShape(Dimens.CornerShape))
    ) {
        Column(modifier = Modifier.padding(Dimens.PaddingColumn)) {
            Text(
                text = cardName,
                style = MaterialTheme.typography.titleMedium.copy(color = textColor),
                modifier = Modifier
                    .padding(bottom = Dimens.PaddingTextBottom)
                    .clickable {
                        onCardNameClick()
                    }
            )
            Text(
                text = Utils.formatCardNumber(cardNumber),
                style = MaterialTheme.typography.bodyMedium.copy(color = textColor),
                modifier = Modifier
                    .padding(bottom = Dimens.PaddingTextBot)
                    .clickable { onCardNumberClick() }
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = Utils.formatExpiryDate(expiryDate),
                    style = MaterialTheme.typography.bodySmall.copy(color = textColor),
                    modifier = Modifier.clickable { onExpiryDateClick() }
                )
                Text(
                    text = cardCompany,
                    style = MaterialTheme.typography.bodySmall.copy(color = textColor),
                    modifier = Modifier.clickable { onCardCompanyClick() }
                )
            }
        }
    }
}

@Composable
private fun ColorCircle(color: Color, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(if (isSelected) Dimens.BoxSize50 else Dimens.BoxSize40)
            .clip(if (isSelected) RectangleShape else CircleShape)
            .background(color)
            .border(
                width = if (isSelected) Dimens.BoxCircleBorder else Dimens.BoxCircleWidth,
                color = if (isSelected) Color.Black else Color.Gray,
                shape = if (isSelected) RectangleShape else CircleShape
            )
            .clickable { onClick() }
    )
}