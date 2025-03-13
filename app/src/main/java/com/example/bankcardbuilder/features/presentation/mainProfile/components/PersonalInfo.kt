package com.example.bankcardbuilder.features.presentation.mainProfile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.example.bankcardbuilder.core.util.Dimens

@Composable
fun PersonalInfo(label: String, value: String?) {
    Box(
        modifier = Modifier
            .height(Dimens.BoxSize50)
            .clip(RoundedCornerShape(Dimens.IconCornerShape))
            .background(
                MaterialTheme.colorScheme.onPrimary
            )
            .padding(horizontal = Dimens.BoxPaddingHorizontal)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "$label ",
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = Dimens.TextFontSp),
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = value ?: "",
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = Dimens.TextFontSize13),
                color = MaterialTheme.colorScheme.onSecondary
            )
        }
    }
}