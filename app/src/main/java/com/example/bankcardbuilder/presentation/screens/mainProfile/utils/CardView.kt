package com.example.bankcardbuilder.presentation.screens.mainProfile.utils

import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.example.bankcardbuilder.R
import com.example.bankcardbuilder.presentation.screens.mainProfile.ShortCardInfo
import com.example.bankcardbuilder.ui.theme.Black
import com.example.bankcardbuilder.ui.theme.Blue
import com.example.bankcardbuilder.ui.theme.BrightOrange
import com.example.bankcardbuilder.ui.theme.DarkBlue
import com.example.bankcardbuilder.ui.theme.DarkGray
import com.example.bankcardbuilder.ui.theme.Red
import com.example.bankcardbuilder.ui.theme.VeryDarkBlue
import com.example.bankcardbuilder.ui.theme.Violet
import com.example.bankcardbuilder.ui.theme.White
import com.example.bankcardbuilder.util.Dimens

@Composable
fun CardView(
    cardInfo: ShortCardInfo,
    isLocked: Boolean,
    onLockToggle: (String) -> Unit,
    goToLoginPinCodeScreen: () -> Unit
) {

    val cardColor = parseColor(cardInfo.color)
    val isDarkTheme = isSystemInDarkTheme()

    val textColor = when (cardColor) {
        Blue, Red, DarkGray, Black, DarkBlue,
        VeryDarkBlue, Violet, BrightOrange -> White

        else -> Black
    }

    val borderColor = when {
        isDarkTheme && cardColor in listOf(DarkGray, Black, VeryDarkBlue) -> Color.White
        !isDarkTheme && cardColor == Color.White && !isLocked -> Color(0xFF5163BF)
        else -> Color.Transparent
    }


    Box(
        modifier = Modifier
            .width(Dimens.BoxSizeWidthDp)
            .height(Dimens.BoxHeight)
            .clip(RoundedCornerShape(Dimens.BoxCornerShape))
            .background(
                if (isLocked) MaterialTheme.colorScheme.secondaryContainer
                else cardColor
            )
            .border(

                width = if (borderColor != Color.Transparent) Dimens.Border else Dimens.BoxBorder0,
                color = borderColor,
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
                    text = cardInfo.paySystem,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        color = textColor,
                        fontSize = Dimens.TextFont
                    )
                )
                Spacer(modifier = Modifier.height(Dimens.SpacerHeight8))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "**** **** **** ${cardInfo.cardNumber.takeLast(4)}",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            color = textColor,
                            fontSize = Dimens.TextFontSp
                        )
                    )
                    IconButton(
                        onClick = { onLockToggle(cardInfo.cardNumber) },
                        modifier = Modifier.padding(start = Dimens.PaddingStartBut)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.lock_open),
                            contentDescription = "Unlock Card",
                            tint = textColor
                        )
                    }
                }
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
                        tint = MaterialTheme.colorScheme.onSecondary,
                        modifier = Modifier.size(Dimens.IconSizeDp)
                    )
                }
            }
        }
    }
}