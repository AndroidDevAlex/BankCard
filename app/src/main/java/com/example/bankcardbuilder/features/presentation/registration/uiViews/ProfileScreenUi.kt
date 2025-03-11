package com.example.bankcardbuilder.features.presentation.registration.uiViews

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import com.example.bankcardbuilder.R
import com.example.bankcardbuilder.core.domain.EmptyFieldException
import com.example.bankcardbuilder.core.domain.Field
import com.example.bankcardbuilder.core.domain.InvalidFieldException
import com.example.bankcardbuilder.core.domain.InvalidFieldFormatException
import com.example.bankcardbuilder.core.presentstion.components.CheckIconCircle
import com.example.bankcardbuilder.core.presentstion.components.CustomTextField
import com.example.bankcardbuilder.core.presentstion.components.TopBarCustom
import com.example.bankcardbuilder.features.presentation.registration.RegistrationState
import com.example.bankcardbuilder.features.presentation.registration.RegistrationUIState
import com.example.bankcardbuilder.core.presentstion.components.rememberImeState
import com.example.bankcardbuilder.core.util.Dimens
import com.example.bankcardbuilder.features.presentation.registration.RegistrationAction


@Composable
fun ProfileScreenUi(
    screenState: RegistrationState,
    actions: (RegistrationAction) -> Unit
) {
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                actions(RegistrationAction.OnPhotoChange(it.toString()))
            }
        }

    val imeState = rememberImeState()
    val scrollState = rememberScrollState()
    val bottomPadding = if (imeState.value) Dimens.Padding16 else Dimens.Padding0

    LaunchedEffect(key1 = imeState.value) {
        if (imeState.value) {
            scrollState.animateScrollTo(scrollState.maxValue)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(MaterialTheme.colorScheme.background)
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .padding(bottom = Dimens.PaddingBut)
    ) {
        TopBarCustom(
            title = stringResource(R.string.profile),
            onBackClicked = {
                actions(RegistrationAction.OnBackClick)
            },
            spacerWidth = Dimens.SpacerWidth70
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
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = Dimens.TextFont,
                    color = MaterialTheme.colorScheme.surface
                ),
            )

            Spacer(modifier = Modifier.height(Dimens.SpacerHeightDp))
            Box(
                modifier = Modifier
                    .size(Dimens.BoxSize134)
                    .clip(CircleShape)
                    .background(
                        MaterialTheme.colorScheme.primary
                    )
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
                        tint = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier.size(Dimens.IconSizeDp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(Dimens.SpacerHeight66))

            val firstNameError =
                (screenState.uiState as? RegistrationUIState.Error)?.exception?.let { exception ->
                    when (exception) {
                        is EmptyFieldException -> if (exception.field == Field.NAME) stringResource(
                            R.string.field_is_empty,
                            exception.field.displayName()
                        ) else null

                        is InvalidFieldFormatException -> if (exception.field == Field.NAME) stringResource(
                            R.string.capital_letter,
                            exception.field.displayName()
                        ) else null

                        is InvalidFieldException -> if (exception.field == Field.NAME) {
                            stringResource(R.string.only_first_letter)
                        } else null

                        else -> null
                    }
                }

            val lastNameError =
                (screenState.uiState as? RegistrationUIState.Error)?.exception?.let { exception ->
                    when (exception) {
                        is EmptyFieldException -> if (exception.field == Field.LASTNAME) stringResource(
                            R.string.last_name_is_Empty
                        ) else null

                        is InvalidFieldFormatException -> if (exception.field == Field.LASTNAME) {
                            stringResource(R.string.field_must_start_with_a_capital_letter)
                        } else null

                        is InvalidFieldException -> if (exception.field == Field.LASTNAME) {
                            stringResource(R.string.only_first_letter)
                        } else null

                        else -> null
                    }
                }

            CustomTextField(
                value = screenState.firstName,
                onValueChange = { actions(RegistrationAction.OnNameChange(it)) },
                label = stringResource(R.string.first_name),
                isError = firstNameError != null,
                errorMessage = firstNameError,
                trailingIcon = {
                    if (screenState.firstName.isNotEmpty()) {
                        CheckIconCircle()
                    }
                }
            )

            Spacer(modifier = Modifier.height(Dimens.SpacerMod))

            CustomTextField(
                value = screenState.lastName,
                onValueChange = { actions(RegistrationAction.OnSurnameChange(it)) },
                label = stringResource(R.string.last_name),
                isError = lastNameError != null,
                errorMessage = lastNameError,
                trailingIcon = {
                    if (screenState.lastName.isNotEmpty()) {
                        CheckIconCircle()
                    }
                }
            )

            Spacer(modifier = Modifier.height(Dimens.SpacerHeight18))

            Button(
                onClick = {
                    actions(RegistrationAction.OnNextClick)
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = Dimens.PaddingTopBut, bottom = bottomPadding)
                    .width(Dimens.ButWidth)
                    .height(Dimens.ButtonHeight),
                shape = RoundedCornerShape(Dimens.CornerShape),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.tertiary
                )
            ) {
                Text(
                    text = stringResource(R.string.set),
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = Dimens.TextFontSize)
                )
            }
        }
    }
}