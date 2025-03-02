package com.example.bankcardbuilder.screens.mainProfile

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.em
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.bankcardbuilder.R
import com.example.bankcardbuilder.exeption.AuthException
import com.example.bankcardbuilder.exeption.CardNotFoundException
import com.example.bankcardbuilder.exeption.SameDataException
import com.example.bankcardbuilder.exeption.StorageException
import com.example.bankcardbuilder.screens.TopBarCustom
import com.example.bankcardbuilder.util.Dimens

@Composable
fun MainProfileScreen(
    goToCardSettingsScreen: () -> Unit,
    logOut: () -> Unit,
    goToLoginPinCodeScreen: (String) -> Unit
) {
    val viewModel = hiltViewModel<MainProfileViewModel>()
    val screenState by viewModel.screenState.collectAsState()
    val context = LocalContext.current

    BackHandler {
        (context as? Activity)?.moveTaskToBack(true)
    }

    MainProfileScreenUi(
        screenState = screenState,
        goToCardSettingsScreen = { goToCardSettingsScreen() },
        onClickLogOut = { viewModel.logOut() },
        logOut = { logOut() },
        onPhotoChange = { viewModel.updatePhoto(it) },
        onLockToggle = { viewModel.toggleCardLock(it) },
        goToLoginPinCodeScreen = { cardNumber ->
            goToLoginPinCodeScreen(cardNumber)
        },
        context = context,
        clearError = { viewModel.clearError() }
    )
}

@Composable
fun MainProfileScreenUi(
    screenState: MainProfileScreenState,
    goToCardSettingsScreen: () -> Unit,
    logOut: () -> Unit,
    onClickLogOut: () -> Unit,
    onPhotoChange: (String) -> Unit,
    onLockToggle: (String) -> Unit,
    goToLoginPinCodeScreen: (String) -> Unit,
    context: Context,
    clearError: () -> Unit
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
                onExitClicked = { onClickLogOut() },
                start = Dimens.ColumnStart,
                end = Dimens.ColumnEnd
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = Dimens.PaddingTop),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(Dimens.SpacerHeight20))

                Text(
                    text = stringResource(R.string.your_profile_information),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = Dimens.TextFontSize18,
                        letterSpacing = 0.003.em
                    ),
                    color = colorResource(id = R.color.grayLabel)
                )

                Spacer(modifier = Modifier.height(Dimens.SpacerHeight35))
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(Dimens.BoxSize150)
                            .clip(CircleShape)
                            .background(colorResource(id = R.color.yellow_Box))
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
                            .size(Dimens.BoxSize38)
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
                            modifier = Modifier.size(Dimens.IconSize19)
                        )

                    }

                }

                Spacer(modifier = Modifier.height(Dimens.SpacerHeight35))

                Column(
                    modifier = Modifier
                        .padding(start = Dimens.ColumnPaddingStart, end = Dimens.ColumnPaddingEnd)
                ) {
                    Text(
                        text = stringResource(R.string.personal_information),
                        style = MaterialTheme.typography.headlineMedium.copy(fontSize = Dimens.FontSizeText20),
                        color = colorResource(id = R.color.orange)
                    )

                    Spacer(modifier = Modifier.height(Dimens.SpacerHeight20))
                    PersonalInfo(
                        label = stringResource(R.string.user_name),
                        value = screenState.userProfile?.userName
                    )

                    Spacer(modifier = Modifier.height(Dimens.SpacerHeight6))
                    PersonalInfo(
                        label = stringResource(R.string.email),
                        value = screenState.userProfile?.email
                    )

                    Spacer(modifier = Modifier.height(Dimens.SpacerHeight6))
                    PersonalInfo(
                        label = stringResource(R.string.mobile_phone),
                        value = screenState.userProfile?.mobilePhone
                    )

                    Spacer(modifier = Modifier.height(Dimens.SpacerHeight38))

                    Text(
                        text = stringResource(R.string.my_cards),
                        style = MaterialTheme.typography.headlineMedium.copy(fontSize = Dimens.TextFontSize),
                        color = colorResource(id = R.color.orange)
                    )

                    Spacer(modifier = Modifier.height(Dimens.SpacerHeight20))
                }
                Column(
                    modifier = Modifier
                        .padding(start = Dimens.PaddingStart3, end = Dimens.PaddingEnd3)
                ) {

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
                    logOut()
                },
                onError = { exception ->
                    val errorMessage = when (exception) {
                        is SameDataException -> stringResource(R.string.this_image_has_already_been_selected)
                        is StorageException -> stringResource(R.string.the_error_occurred_while_updating_the_data)
                        is CardNotFoundException -> stringResource(R.string.card_not_found)
                        is AuthException -> context.getString(R.string.this_user_does_not_exist)
                        else -> stringResource(R.string.an_unknown_error_occurred)
                    }

                    Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
                    clearError()
                },
                onEmpty = {}
            )
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