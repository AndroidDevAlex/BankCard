package com.example.bankcardbuilder.screens.settingsCard

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.example.bankcardbuilder.R
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
                        if (isFilled) colorResource(id = R.color.orange) else Color.Transparent,
                    )
                    .border(
                        Dimens.BoxBorder,
                        if (isFilled) colorResource(id = R.color.orange) else Color.Gray,
                        CircleShape
                    )
            )
        }
    }
}