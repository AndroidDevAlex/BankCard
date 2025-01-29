package com.example.bankcardbuilder.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.bankcardbuilder.R
import com.example.bankcardbuilder.exeption.AppException
import com.example.bankcardbuilder.ui.theme.BoxError
import com.example.bankcardbuilder.ui.theme.TextError
import com.example.bankcardbuilder.util.Dimens

@Composable
fun UiStateHandler(
    isLoading: Boolean,
    isSuccess: Boolean = false,
    isError: AppException?,
    isEmpty: Boolean,
    isLoggedOut: Boolean = false,
    onError: @Composable (AppException) -> Unit,
    successMessage: String?,
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
            successMessage?.let { message ->
                SuccessState(
                    message = message,
                    onSuccess = { onSuccess?.invoke() }
                )
            }
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
            color = colorResource(id = R.color.black),
            modifier = Modifier.size(Dimens.CircularProgress)
        )
    }
}

@Composable
private fun SuccessState(message: String, onSuccess: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Dimens.PaddingVertical),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            color = Color.Blue,
            style = MaterialTheme.typography.headlineMedium.copy(fontSize = Dimens.TextFontSize),
        )
    }
    LaunchedEffect(Unit) {
        onSuccess()
    }
}

@Composable
fun CustomError(
    error: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.headlineMedium.copy(fontSize = Dimens.TextFontSize)
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = BoxError,
                shape = RoundedCornerShape(Dimens.CornerShape)
            )
            .then(Modifier.padding(Dimens.Padding)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = error,
            color = TextError,
            style = textStyle,
            fontWeight = FontWeight.W500,
            textAlign = TextAlign.Center
        )
    }
}