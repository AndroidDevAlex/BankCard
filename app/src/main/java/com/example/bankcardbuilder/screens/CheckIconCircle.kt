package com.example.bankcardbuilder.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.bankcardbuilder.util.Dimens

@Composable
fun CheckIconCircle() {
    Box(
        modifier = Modifier
            .size(Dimens.BoxSize21)
            .background(
                color = MaterialTheme.colorScheme.primary
                , shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = "Check Icon",
            tint = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.size(Dimens.IconSize14)
        )
    }
}