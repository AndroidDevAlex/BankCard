package com.example.bankcardbuilder.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.example.bankcardbuilder.R
import com.example.bankcardbuilder.util.Dimens

@Composable
fun CheckIconCircle() {
    Box(
        modifier = Modifier
            .size(Dimens.BoxSize21)
            .background(color = colorResource(id = R.color.orange), shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = "Check Icon",
            tint = Color.White,
            modifier = Modifier.size(Dimens.IconSize14)
        )
    }
}