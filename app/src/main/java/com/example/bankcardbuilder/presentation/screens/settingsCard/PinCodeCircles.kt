package com.example.bankcardbuilder.presentation.screens.settingsCard

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.example.bankcardbuilder.util.Dimens

@Composable
fun PinCodeCircles(
    pinCodeLength: Int
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(Dimens.HorizontalCircleSpacedBy),
    ) {
        repeat(5) { index ->
            val isFilled = index < pinCodeLength
            Box(
                modifier = Modifier
                    .size(Dimens.BoxSizeCircle)
                    .clip(CircleShape)
                    .background(
                        if (isFilled)
                            MaterialTheme.colorScheme.primary
                        else Color.Transparent,
                    )
                    .border(
                        Dimens.Border,
                        if (isFilled)
                            MaterialTheme.colorScheme.primary
                        else
                            Color.Gray,
                        CircleShape
                    )
            )
        }
    }
}