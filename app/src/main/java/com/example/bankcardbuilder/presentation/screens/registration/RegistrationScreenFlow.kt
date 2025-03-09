package com.example.bankcardbuilder.presentation.screens.registration

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bankcardbuilder.presentation.screens.registration.uiViews.PhoneNumberScreenUi
import com.example.bankcardbuilder.presentation.screens.registration.uiViews.ProfileScreenUi
import com.example.bankcardbuilder.presentation.screens.registration.uiViews.SecurityQuestionScreenUi
import com.example.bankcardbuilder.presentation.screens.registration.uiViews.SetupEmailPasswordScreenUi

@Composable
fun RegistrationScreenFlow(
    goToMainScreen: () -> Unit
) {
    val viewModel = hiltViewModel<RegistrationViewModel>()
    val screenState by viewModel.state.collectAsState()
    val context = LocalContext.current


    val actions = RegistrationActions(
        goToMainScreen = goToMainScreen,
        onEmailChange = { viewModel.updateState(screenState.copy(email = it)) },
        onPasswordChange = { viewModel.updateState(screenState.copy(password = it)) },
        onConfirmPasswordChange = { viewModel.updateState(screenState.copy(confirmPassword = it)) },
        onTogglePasswordVisibility = {
            viewModel.updateState(screenState.copy(isPasswordVisible = !screenState.isPasswordVisible))
        },
        onToggleConfirmPasswordVisibility = {
            viewModel.updateState(screenState.copy(isConfirmPasswordVisible = !screenState.isConfirmPasswordVisible))
        },
        onNextClick = { viewModel.validation() },
        onNameChange = { viewModel.updateState(screenState.copy(firstName = it)) },
        onSurnameChange = { viewModel.updateState(screenState.copy(lastName = it)) },
        onPhotoChange = { viewModel.updateState(screenState.copy(photo = it)) },
        onPhoneNumberChange = { viewModel.updateState(screenState.copy(phoneNumber = it)) },
        onAnswerChange = { viewModel.updateState(screenState.copy(answer = it)) },
        onBackClick = { viewModel.goToPreviousStep() },
        clearError = { viewModel.clearError() }
    )

    when (screenState.currentStep) {

        RegistrationStep.SIGN_UP -> {
            SetupEmailPasswordScreenUi(screenState, actions)
        }
        RegistrationStep.PROFILE -> {
            ProfileScreenUi(screenState, actions)
        }
        RegistrationStep.PHONE -> {
            PhoneNumberScreenUi(screenState, actions)
        }
        RegistrationStep.SECURITY_QUESTION -> {
            SecurityQuestionScreenUi(screenState, actions, context)
        }
        else -> {}
    }
}