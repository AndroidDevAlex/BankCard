package com.example.bankcardbuilder.features.presentation.registration

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bankcardbuilder.features.presentation.registration.uiViews.PhoneNumberScreenUi
import com.example.bankcardbuilder.features.presentation.registration.uiViews.ProfileScreenUi
import com.example.bankcardbuilder.features.presentation.registration.uiViews.SecurityQuestionScreenUi
import com.example.bankcardbuilder.features.presentation.registration.uiViews.SetupEmailPasswordScreenUi

@Composable
fun RegistrationScreenFlow(
    goToMainScreen: () -> Unit
) {
    val viewModel = hiltViewModel<RegistrationViewModel>()
    val screenState by viewModel.state.collectAsState()
    val context = LocalContext.current

    val handleAction: (RegistrationAction) -> Unit = { action ->
        when (action) {
            is RegistrationAction.GoToMainScreen -> goToMainScreen()
            else -> Unit
        }
        viewModel.onAction(action)
    }

    when (screenState.currentStep) {
        is RegistrationStep.SIGN_UP -> SetupEmailPasswordScreenUi(screenState, handleAction)
        is RegistrationStep.PROFILE -> ProfileScreenUi(screenState, handleAction)
        is RegistrationStep.PHONE -> PhoneNumberScreenUi(screenState, handleAction)
        is RegistrationStep.SECURITY_QUESTION -> SecurityQuestionScreenUi(
            screenState,
            handleAction,
            context
        )

        else -> {}
    }
}