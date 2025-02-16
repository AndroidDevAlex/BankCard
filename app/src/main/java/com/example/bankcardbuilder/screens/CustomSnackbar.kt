package com.example.bankcardbuilder.screens

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.bankcardbuilder.util.Dimens

@Composable
fun CustomInfoSnackbar(
    snackbar: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    SnackbarHost(hostState = snackbar) { data ->
        Snackbar(
            snackbarData = data,
            modifier = modifier
                .padding(bottom = Dimens.PaddingBot80)
                .height(Dimens.ModHeight),
            containerColor = Color.DarkGray,
            contentColor = Color.White
        )
    }
}