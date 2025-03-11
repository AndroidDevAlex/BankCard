package com.example.bankcardbuilder.features.presentation.mainProfile

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
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.em
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.bankcardbuilder.R
import com.example.bankcardbuilder.core.domain.AuthException
import com.example.bankcardbuilder.core.domain.CardNotFoundException
import com.example.bankcardbuilder.core.domain.SameDataException
import com.example.bankcardbuilder.core.domain.StorageException
import com.example.bankcardbuilder.features.presentation.mainProfile.components.CardView
import com.example.bankcardbuilder.features.presentation.mainProfile.components.PersonalInfo
import com.example.bankcardbuilder.features.presentation.mainProfile.components.UiStateHandler
import com.example.bankcardbuilder.core.presentstion.components.TopBarCustom
import com.example.bankcardbuilder.core.util.Dimens

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
        actions = { action ->
            when (action) {
                is MainProfileAction.GoToCardSettingsScreen -> goToCardSettingsScreen()
                is MainProfileAction.GoToLoginPinCodeScreen -> goToLoginPinCodeScreen(action.pinCode)
                is MainProfileAction.OnLogOut -> logOut()
                else -> viewModel.onAction(action)
            }
        },
        context = context
    )
}

@Composable
fun MainProfileScreenUi(
    screenState: MainProfileScreenState,
    actions: (MainProfileAction) -> Unit,
    context: Context
) {

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let { actions(MainProfileAction.OnUpdatePhoto(it.toString())) }
        }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(bottom = Dimens.PaddingBottom100)
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    bottom = Dimens.ColumnPadding,
                    start = Dimens.BoxHorizontal,
                    end = Dimens.BoxHorizontal
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopBarCustom(
                onMenuClicked = {},
                onExitClicked = { actions(MainProfileAction.OnClickLogOut) },
                start = Dimens.ColumnStart,
                end = Dimens.ColumnEnd
            )

            Spacer(modifier = Modifier.height(Dimens.SpacerHeight20))

            Text(
                text = stringResource(R.string.your_profile_information),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = Dimens.TextFontSize18,
                    letterSpacing = 0.003.em
                ),
                color = MaterialTheme.colorScheme.onTertiary
            )

            Spacer(modifier = Modifier.height(Dimens.SpacerHeight35))

            Box(contentAlignment = Alignment.Center) {
                Box(
                    modifier = Modifier
                        .size(Dimens.BoxSize150)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.tertiaryContainer)
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
                        .background(MaterialTheme.colorScheme.primary)
                        .align(Alignment.BottomEnd)
                        .clickable { launcher.launch("image/*") },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.edit_modify),
                        contentDescription = "Edit Icon",
                        tint = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier.size(Dimens.IconSize19)
                    )
                }
            }

            Spacer(modifier = Modifier.height(Dimens.SpacerHeight35))

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.personal_information),
                    style = MaterialTheme.typography.headlineMedium.copy(fontSize = Dimens.FontSizeText20),
                    color = MaterialTheme.colorScheme.primary
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
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = if (screenState.userProfile?.cards.isNullOrEmpty()) Dimens.Padding0 else Dimens.BoxHorizontal,
                    end = if (screenState.userProfile?.cards.isNullOrEmpty()) Dimens.Padding0 else Dimens.Padding1
                )
                .align(Alignment.BottomCenter)
        ) {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = if (screenState.userProfile?.cards.isNullOrEmpty()) {
                    Arrangement.Center
                } else {
                    Arrangement.spacedBy(Dimens.SpacedBy)
                }

            ) {
                if (screenState.userProfile?.cards.isNullOrEmpty()) {
                    item {
                        AddNewCardBox(onClick = {
                            actions(MainProfileAction.GoToCardSettingsScreen)
                        })
                    }
                } else {
                    items(screenState.userProfile?.cards ?: emptyList()) { cardState ->
                        CardView(
                            cardInfo = cardState,
                            isLocked = cardState.isLocked,
                            onLockToggle = { cardNumber ->
                                actions(MainProfileAction.OnToggleCardLock(cardNumber))
                            },
                            goToLoginPinCodeScreen = {
                                actions(MainProfileAction.GoToLoginPinCodeScreen(cardState.cardNumber))
                            }
                        )
                    }
                    item {
                        AddNewCardBox(onClick = { actions(MainProfileAction.GoToCardSettingsScreen) })
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
                onLoggedOut = { actions(MainProfileAction.OnLogOut) },
                onError = { exception ->
                    val errorMessage = when (exception) {
                        is SameDataException -> stringResource(R.string.this_image_has_already_been_selected)
                        is StorageException -> stringResource(R.string.the_error_occurred_while_updating_the_data)
                        is CardNotFoundException -> stringResource(R.string.card_not_found)
                        is AuthException -> context.getString(R.string.this_user_does_not_exist)
                        else -> stringResource(R.string.an_unknown_error_occurred)
                    }

                    Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
                    actions(MainProfileAction.ClearError)
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
            .background(
                MaterialTheme.colorScheme.onTertiary
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add New Card",
            tint = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.size(Dimens.IconSizeImage)
        )
    }
}