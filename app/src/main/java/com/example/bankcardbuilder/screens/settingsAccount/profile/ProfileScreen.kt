package com.example.bankcardbuilder.screens.settingsAccount.profile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.text.TextStyle
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.bankcardbuilder.R
import com.example.bankcardbuilder.screens.TopBarCustom
import com.example.bankcardbuilder.exeption.EmptyFieldException
import com.example.bankcardbuilder.exeption.InvalidFieldFormatException
import com.example.bankcardbuilder.exeption.StorageException
import com.example.bankcardbuilder.screens.CustomError
import com.example.bankcardbuilder.screens.UiStateHandler
import com.example.bankcardbuilder.util.Dimens

@Composable
fun ProfileScreen(
    goToLoginScreen: () -> Unit,
    goToPhoneNumberScreen: () -> Unit
) {
    val viewModel = hiltViewModel<ProfileScreenViewModel>()
    val uiState by viewModel.uiState.collectAsState()

    ProfileScreenUi(
        screenState = uiState,
        goToLoginScreen = { goToLoginScreen() },
        goToPhoneNumberScreen = { goToPhoneNumberScreen() },
        onNameChange = { viewModel.updateScreenState(uiState.copy(firstName = it)) },
        onSurnameChange = { viewModel.updateScreenState(uiState.copy(lastName = it)) },
        onSaveDataClick = { viewModel.saveProfile() },
        onPhotoChange = { viewModel.updateScreenState(uiState.copy(photo = it)) }
    )
}

@Composable
private fun ProfileScreenUi(
    screenState: ProfileScreenState,
    goToLoginScreen: () -> Unit,
    goToPhoneNumberScreen: () -> Unit,
    onNameChange: (String) -> Unit,
    onSurnameChange: (String) -> Unit,
    onSaveDataClick: () -> Unit,
    onPhotoChange: (String) -> Unit,
) {

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let { onPhotoChange(it.toString()) }
        }

    Column(modifier = Modifier.fillMaxSize()) {
        TopBarCustom(
            title = stringResource(R.string.profile),
            onBackClicked = { goToLoginScreen() }
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
            Spacer(modifier = Modifier.height(Dimens.SpacerHeight36))

            Text(
                text = stringResource(R.string.please_set_up_your_profile),
                style = MaterialTheme.typography.titleMedium,
                color = colorResource(id = R.color.gray),
            )

            Spacer(modifier = Modifier.height(Dimens.SpacerHeightDp))
            Box(
                modifier = Modifier
                    .size(Dimens.BoxSizeMod)
                    .clip(CircleShape)
                    .background(colorResource(id = R.color.orange))
                    .clickable { launcher.launch("image/*") },
                contentAlignment = Alignment.Center
            ) {
                if (screenState.photo != null) {
                    AsyncImage(
                        model = screenState.photo,
                        contentDescription = "Profile Photo",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.download),
                        contentDescription = "Download Icon",
                        tint = Color.White,
                        modifier = Modifier.size(Dimens.IconSizeProf)
                    )
                }
            }

            Spacer(modifier = Modifier.height(Dimens.Height))

            TextField(
                value = screenState.firstName,
                onValueChange = onNameChange,
                label = { Text(text = stringResource(R.string.first_name)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = Dimens.BoxPaddingVertical),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorResource(id = R.color.orange),
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = colorResource(id = R.color.orange),
                    unfocusedLabelColor = Color.Gray,
                    cursorColor = colorResource(id = R.color.orange)
                ),
                textStyle = TextStyle(
                    color = MaterialTheme.colorScheme.onSurface
                ),
                trailingIcon = {
                    if (screenState.firstName.isNotEmpty()) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Clear",
                            tint = colorResource(id = R.color.orange),
                            modifier = Modifier
                                .size(Dimens.IconSizeDp)
                        )
                    }
                }
            )

            TextField(
                value = screenState.lastName,
                onValueChange = onSurnameChange,
                label = { Text(text = stringResource(R.string.last_name)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = Dimens.BoxPaddingVertical),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorResource(id = R.color.orange),
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = colorResource(id = R.color.orange),
                    unfocusedLabelColor = Color.Gray,
                    cursorColor = colorResource(id = R.color.orange)
                ),
                textStyle = TextStyle(
                    color = MaterialTheme.colorScheme.onSurface
                ),

                trailingIcon = {
                    if (screenState.lastName.isNotEmpty()) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Clear",
                            tint = colorResource(id = R.color.orange),
                            modifier = Modifier
                                .size(Dimens.IconSizeDp)
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(Dimens.SpacerHeight34))

            UiStateHandler(
                isLoading = screenState.stateUI is ProfileUIState.Loading,
                isSuccess = screenState.stateUI is ProfileUIState.Success,
                isError = (screenState.stateUI as? ProfileUIState.Error)?.exception,
                isEmpty = screenState.stateUI is ProfileUIState.Empty,
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
                onSuccess = { goToPhoneNumberScreen() },
                onEmpty = {}
            )

            Button(
                onClick = {
                    onSaveDataClick()

                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = Dimens.PaddingTopBut)
                    .width(Dimens.ButWidth)
                    .height(Dimens.ButtonHeight),
                shape = RoundedCornerShape(Dimens.CornerShape),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.orange),
                    contentColor = Color.White
                )
            ) {
                Text(text = stringResource(R.string.set), style = MaterialTheme.typography.titleLarge)
            }
        }
    }
}