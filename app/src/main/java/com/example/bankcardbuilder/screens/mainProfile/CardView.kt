package com.example.bankcardbuilder.screens.mainProfile

import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.example.bankcardbuilder.R
import com.example.bankcardbuilder.ui.theme.Aquamarine
import com.example.bankcardbuilder.ui.theme.Beige
import com.example.bankcardbuilder.util.Dimens

@Composable
fun CardView(
    cardInfo: ShortCardInfo,
    isLocked: Boolean,
    onLockToggle: (String) -> Unit,
    goToLoginPinCodeScreen: () -> Unit
) {

    val cardColor = parseColor(cardInfo.color)
    val textColor = when (cardColor) {
        Color.LightGray, Color.Green, Color.Yellow, Color.Cyan, Color.White, Beige, Aquamarine -> Color.Black
        else -> Color.White
    }

    Box(
        modifier = Modifier
            .width(Dimens.BoxSizeWidth)
            .height(Dimens.BoxHeight)
            .clip(RoundedCornerShape(Dimens.BoxCornerShape))
            .background(if (isLocked) Color.LightGray else cardColor)
            .border(
                width = if (cardColor == Color.White && !isLocked) Dimens.BoxBorder else Dimens.BoxBorder0,
                color = Color.Gray,
                shape = RoundedCornerShape(Dimens.BoxCornerShape)
            )
            .padding(Dimens.PaddingBox)
    ) {
        if (!isLocked) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = cardInfo.company,
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = Dimens.TextFont),
                    color = textColor,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(Dimens.SpacerHeight8))
                Text(
                    text = cardInfo.cardNumber.chunked(4).joinToString(" "),
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = Dimens.TextFontSp),
                    color = textColor
                )
            }
            IconButton(
                onClick = { onLockToggle(cardInfo.cardNumber) },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(Dimens.IconButtonPadding)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.lock_open),
                    contentDescription = "Unlock Card",
                    tint = textColor
                )
            }
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = {
                        goToLoginPinCodeScreen()
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.lock_close),
                        contentDescription = "Locked Card",
                        tint = Color.Black,
                        modifier = Modifier.size(Dimens.IconSizeDp)
                    )
                }
            }
        }
    }
}