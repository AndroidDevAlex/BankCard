package com.example.bankcardbuilder.screens.registration

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bankcardbuilder.screens.registration.uiViews.PhoneNumberScreenUi
import com.example.bankcardbuilder.screens.registration.uiViews.ProfileScreenUi
import com.example.bankcardbuilder.screens.registration.uiViews.SecurityQuestionScreenUi
import com.example.bankcardbuilder.screens.registration.uiViews.SetupEmailPasswordScreenUi

@Composable
fun RegistrationScreenFlow(
    goToMainScreen: () -> Unit
) {
    val viewModel = hiltViewModel<RegistrationViewModel>()
    val screenState by viewModel.state.collectAsState()
    val context = LocalContext.current

    when (screenState.currentStep) {
        RegistrationStep.SIGN_UP -> {
            SetupEmailPasswordScreenUi(
                screenState = screenState,
                onEmailChange = { viewModel.updateState(screenState.copy(email = it)) },
                onPasswordChange = { viewModel.updateState(screenState.copy(password = it)) },
                onConfirmPasswordChange = { viewModel.updateState(screenState.copy(confirmPassword = it)) },
                onTogglePasswordVisibility = {
                    viewModel.updateState(screenState.copy(isPasswordVisible = !screenState.isPasswordVisible))
                },
                onToggleConfirmPasswordVisibility = {
                    viewModel.updateState(screenState.copy(isConfirmPasswordVisible = !screenState.isConfirmPasswordVisible))
                },
                onNextClick = { viewModel.validation() }
            )

        }
        RegistrationStep.PROFILE-> {
            ProfileScreenUi(
                screenState = screenState,
                onNameChange = { viewModel.updateState(screenState.copy(firstName = it)) },
                onSurnameChange = { viewModel.updateState(screenState.copy(lastName = it)) },
                onPhotoChange = { viewModel.updateState(screenState.copy(photo = it)) },
                onNextClick = { viewModel.validation() },
                onBackClick = { viewModel.goToPreviousStep() }
            )
        }
        RegistrationStep.PHONE -> {
            PhoneNumberScreenUi(
                screenState = screenState,
                onPhoneNumberChange = { viewModel.updateState(screenState.copy(phoneNumber = it)) },
                onBackClick = { viewModel.goToPreviousStep() },
                onNextClick = { viewModel.validation() }
            )
        }
        RegistrationStep.SECURITY_QUESTION -> {
            SecurityQuestionScreenUi(
                screenState = screenState,
                onAnswerChange = { viewModel.updateState(screenState.copy(answer = it)) },
                onBackClick = { viewModel.goToPreviousStep() },
                onNextClick = { viewModel.validation() },
                goToMainScreen = { goToMainScreen() },
                clearError = { viewModel.clearError()},
                context = context
            )
        }
        else -> {}
    }
}