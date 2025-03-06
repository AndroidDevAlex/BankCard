package com.example.bankcardbuilder.screens.mainProfile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.bankcardbuilder.exeption.AppException
import com.example.bankcardbuilder.util.Dimens


@Composable
fun UiStateHandler(
    isLoading: Boolean,
    isSuccess: Boolean = false,
    isError: AppException?,
    isEmpty: Boolean,
    isLoggedOut: Boolean = false,
    onError: @Composable (AppException) -> Unit,
    onSuccess: (() -> Unit)? = null,
    onLoggedOut: (() -> Unit)? = null,
    onEmpty: @Composable () -> Unit,
) {
    when {
        isLoading -> {
            LoadingState()
        }

        isError != null -> onError(isError)

        isSuccess -> {
            SuccessState(
                onSuccess = { onSuccess?.invoke() }
            )
        }

        isLoggedOut -> {
            LaunchedEffect(Unit) {
                onLoggedOut?.invoke()
            }
        }

        isEmpty -> {
            onEmpty()
        }
    }
}

@Composable
private fun LoadingState() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Dimens.BoxPaddingVertical),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.size(Dimens.CircularProgress)
        )
    }
}

@Composable
private fun SuccessState(onSuccess: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Dimens.PaddingVertical),
        contentAlignment = Alignment.Center
    ) {}
    LaunchedEffect(Unit) {
        onSuccess()
    }
}