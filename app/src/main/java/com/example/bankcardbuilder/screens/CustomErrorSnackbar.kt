package com.example.bankcardbuilder.screens

import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.bankcardbuilder.util.Dimens

@Composable
fun CustomErrorSnackbar(
    snackbar: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    SnackbarHost(hostState = snackbar) { data ->
        Snackbar(
            snackbarData = data,
            modifier = modifier
                .padding(bottom = Dimens.PaddingBot80)
                .heightIn(min = Dimens.MinHeightSnack, max = Dimens.MaxHeightSnack),
            containerColor = MaterialTheme.colorScheme.errorContainer,
            contentColor = MaterialTheme.colorScheme.onErrorContainer,
        )
    }
}