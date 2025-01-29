package com.example.bankcardbuilder.screens.settingsAccount.main

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.bankcardbuilder.R
import com.example.bankcardbuilder.screens.TopBarCustom
import com.example.bankcardbuilder.exeption.CardNotFoundException
import com.example.bankcardbuilder.exeption.SameDataException
import com.example.bankcardbuilder.exeption.StorageException
import com.example.bankcardbuilder.screens.CustomError
import com.example.bankcardbuilder.screens.UiStateHandler
import com.example.bankcardbuilder.ui.theme.Aquamarine
import com.example.bankcardbuilder.ui.theme.Beige
import com.example.bankcardbuilder.ui.theme.Orange
import com.example.bankcardbuilder.util.Dimens

@Composable
fun MainProfileScreen(
    goToCardSettingsScreen: () -> Unit,
    goToLogIn: () -> Unit,
    goToLoginPinCodeScreen: (String) -> Unit
) {
    val viewModel = hiltViewModel<MainProfileViewModel>()
    val screenState by viewModel.screenState.collectAsState()

    MainProfileScreenUi(
        screenState = screenState,
        goToCardSettingsScreen = { goToCardSettingsScreen() },
        goToLogIn = { goToLogIn() },
        onClickLogOut = { viewModel.logOut() },
        onPhotoChange = { viewModel.updatePhoto(it) },
        onLockToggle = { viewModel.toggleCardLock(it) },
        goToLoginPinCodeScreen = { cardNumber ->
            goToLoginPinCodeScreen(cardNumber)
        }
    )
}

@Composable
private fun MainProfileScreenUi(
    screenState: MainProfileScreenState,
    goToCardSettingsScreen: () -> Unit,
    goToLogIn: () -> Unit,
    onClickLogOut: () -> Unit,
    onPhotoChange: (String) -> Unit,
    onLockToggle: (String) -> Unit,
    goToLoginPinCodeScreen: (String) -> Unit
) {

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let { onPhotoChange(it.toString()) }
        }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Dimens.BoxHorizontal)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = Dimens.ColumnPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopBarCustom(
                onMenuClicked = {},
                onExitClicked = { onClickLogOut() }
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = Dimens.PaddingTop),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(Dimens.SpacerHeight36))

                Text(
                    text = stringResource(R.string.your_profile_information),
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = Dimens.TextFontSize),
                    color = colorResource(id = R.color.gray),
                    fontWeight = FontWeight.Bold,
                )

                Spacer(modifier = Modifier.height(Dimens.SpacerHeightDp))
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(Dimens.BoxSizeMod)
                            .clip(CircleShape)
                            .background(colorResource(id = R.color.yellow))
                    ) {
                        AsyncImage(
                            model = screenState.userProfile?.photo,
                            contentDescription = "Profile Photo",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                    Box(
                        modifier = Modifier
                            .size(Dimens.BoxSizeDp)
                            .clip(CircleShape)
                            .background(colorResource(id = R.color.orange))
                            .align(Alignment.BottomEnd)
                            .clickable { launcher.launch("image/*") },
                        contentAlignment = Alignment.Center
                    ) {

                        Icon(
                            painter = painterResource(id = R.drawable.edit_modify),
                            contentDescription = "Edit Icon",
                            tint = Color.White,
                            modifier = Modifier.size(Dimens.IconSizeBack)
                        )

                    }

                }

                Spacer(modifier = Modifier.height(Dimens.Height))

                Column {
                    Text(
                        text = stringResource(R.string.personal_information),
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = Dimens.TextSize),
                        color = colorResource(id = R.color.orange),
                        fontWeight = FontWeight.Bold,
                    )

                    Spacer(modifier = Modifier.height(Dimens.SpacerHeight20))
                    PersonalInfo(label = stringResource(R.string.user_name), value = screenState.userProfile?.userName)

                    Spacer(modifier = Modifier.height(Dimens.SpacerHeight12))
                    PersonalInfo(label = stringResource(R.string.email), value = screenState.userProfile?.email)

                    Spacer(modifier = Modifier.height(Dimens.SpacerHeight12))
                    PersonalInfo(
                        label = stringResource(R.string.mobile_phone),
                        value = screenState.userProfile?.mobilePhone
                    )


                    Spacer(modifier = Modifier.height(Dimens.SpacerHeight60))

                    Text(
                        text = stringResource(R.string.my_cards),
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = Dimens.TextSize),
                        color = colorResource(id = R.color.orange),
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(Dimens.SpacerHeight20))

                    if (screenState.userProfile?.cards.isNullOrEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(Dimens.BoxHeight)
                                .padding(bottom = Dimens.BoxBottom),
                            contentAlignment = Alignment.Center
                        ) {
                            AddNewCardBox(onClick = {
                                goToCardSettingsScreen()
                            })
                        }
                    } else {
                        LazyRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(Dimens.SpacedBy)
                        ) {
                            items(screenState.userProfile?.cards ?: emptyList()) { cardState ->
                                CardView(
                                    cardInfo = cardState,
                                    isLocked = cardState.isLocked,
                                    onLockToggle = onLockToggle,
                                    goToLoginPinCodeScreen = { goToLoginPinCodeScreen(cardState.cardNumber) }
                                )
                            }
                            item {
                                AddNewCardBox(onClick = { goToCardSettingsScreen() })
                            }
                        }
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(Dimens.BoxBottom),
            contentAlignment = Alignment.Center
        ) {

            UiStateHandler(
                isLoading = screenState.stateUI is MainProfileUiState.Loading,
                isError = (screenState.stateUI as? MainProfileUiState.Error)?.exception,
                isEmpty = screenState.stateUI is MainProfileUiState.Empty,
                isLoggedOut = screenState.stateUI is MainProfileUiState.LoggedOut,
                onLoggedOut = {
                    goToLogIn()
                },
                onError = { exception ->
                    val errorMessage = when (exception) {
                        is SameDataException -> stringResource(R.string.this_image_has_already_been_selected)
                        is StorageException -> stringResource(R.string.the_error_occurred_while_updating_the_data)
                        is CardNotFoundException -> stringResource(R.string.card_not_found)
                        else -> stringResource(R.string.an_unknown_error_occurred)
                    }
                    CustomError(errorMessage, modifier = Modifier.padding(Dimens.PaddingMod),
                        textStyle = MaterialTheme.typography.bodyMedium.copy(fontSize = Dimens.TextSize))
                },
                successMessage = null,
                onEmpty = {}
            )
        }
    }
}

@Composable
private fun CardView(
    cardInfo: ShortCardInfo,
    isLocked: Boolean,
    onLockToggle: (String) -> Unit,
    goToLoginPinCodeScreen: () -> Unit
) {

    val cardColor = parseColor(cardInfo.color)
    val textColor = when (cardColor) {
        Color.LightGray, Color.Green, Color.Yellow, Color.Cyan, Color.White, Beige, Aquamarine -> Color.Black
        else -> Color.White
    }

    Box(
        modifier = Modifier
            .width(Dimens.BoxSizeWidth)
            .height(Dimens.BoxHeight)
            .clip(RoundedCornerShape(Dimens.BoxCornerShape))
            .background(if (isLocked) Color.LightGray else cardColor)
            .border(
                width = if (cardColor == Color.White && !isLocked) Dimens.BoxBorder else Dimens.BoxBorder0,
                color = Color.Gray,
                shape = RoundedCornerShape(Dimens.BoxCornerShape)
            )
            .padding(Dimens.PaddingBox)
    ) {
        if (!isLocked) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = cardInfo.company,
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = Dimens.TextFont),
                    color = textColor,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(Dimens.SpacerHeight8))
                Text(
                    text = cardInfo.cardNumber.chunked(4).joinToString(" "),
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = Dimens.TextFontSp),
                    color = textColor
                )
            }
            IconButton(
                onClick = { onLockToggle(cardInfo.cardNumber) },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(Dimens.IconButtonPadding)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.lock_open),
                    contentDescription = "Unlock Card",
                    tint = textColor
                )
            }
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = {
                        goToLoginPinCodeScreen()
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.lock_close),
                        contentDescription = "Locked Card",
                        tint = Color.Black,
                        modifier = Modifier.size(Dimens.IconSizeDp)
                    )
                }
            }
        }
    }
}

@Composable
private fun AddNewCardBox(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .width(Dimens.BoxSizeWidthDp)
            .height(Dimens.BoxHeight)
            .clip(RoundedCornerShape(Dimens.BoxCornerShape))
            .background(Color.Gray)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add New Card",
            tint = Color.White,
            modifier = Modifier.size(Dimens.IconSizeImage)
        )
    }
}

@Composable
private fun PersonalInfo(label: String, value: String?) {
    Box(
        modifier = Modifier
            .height(Dimens.BoxSize50)
            .clip(RoundedCornerShape(Dimens.IconCornerShape))
            .background(colorResource(id = R.color.light_gray))
            .padding(horizontal = Dimens.BoxPaddingHorizontal)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "$label ",
                style = MaterialTheme.typography.bodySmall.copy(fontSize = Dimens.FontSizeRow),
                color = Orange
            )

            Text(
                text = value ?: "",
                style = MaterialTheme.typography.bodySmall.copy(fontSize = Dimens.RowFontSize),
                color = Color.Black
            )
        }
    }
}