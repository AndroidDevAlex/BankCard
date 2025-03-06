package com.example.bankcardbuilder.screens.settingsCard.cardSettings

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import com.example.bankcardbuilder.ui.theme.Black
import com.example.bankcardbuilder.ui.theme.DarkGray
import com.example.bankcardbuilder.ui.theme.VeryDarkBlue
import com.example.bankcardbuilder.util.Dimens
import com.example.bankcardbuilder.util.Utils

@Composable
fun BankCard(
    color: Color,
    cardName: String,
    cardNumber: String,
    expiryDate: String,
    cardPaySystem: String,
    textColor: Color,
    onCardNameClick: () -> Unit,
    onCardNumberClick: () -> Unit,
    onCardPaySystemClick: () -> Unit,
    onExpiryDateClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    val isDarkTheme = isSystemInDarkTheme()
    val shouldHaveWhiteBorder =
        isDarkTheme && (color == DarkGray || color == Black || color == VeryDarkBlue)


    Box(
        modifier = modifier
            .size(Dimens.BoxSize310, Dimens.BoxSize171)
            .shadow(
                elevation = if (color == Color.White) Dimens.BoxElevation else Dimens.BoxElevation0,
                shape = RoundedCornerShape(Dimens.RoundedCornerShape22)
            )
            .clip(RoundedCornerShape(Dimens.RoundedCornerShape22))
            .background(color)
            .then(
                if (shouldHaveWhiteBorder) Modifier.border(
                    Dimens.Border,
                    Color.White,
                    RoundedCornerShape(Dimens.RoundedCornerShape22)
                )
                else Modifier
            )
    ) {
        Column(
            modifier = Modifier.padding(
                top = Dimens.ColumnTop,
                bottom = Dimens.ColumnBottom,
                start = Dimens.ColumnStart14,
                end = Dimens.ColumnEnd14
            )
        ) {
            Text(
                text = cardName,
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = textColor,
                    fontSize = Dimens.TextFont12
                ),
                modifier = Modifier
                    .padding(bottom = Dimens.PaddingBottom7)
                    .clickable { onCardNameClick() }
            )
            Text(
                text = Utils.formatCardNumber(cardNumber),
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = textColor,
                    fontSize = Dimens.TextFont
                ),
                modifier = Modifier
                    .padding(bottom = Dimens.PaddingTextBot)
                    .clickable { onCardNumberClick() }
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "A Debit Card",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = textColor,
                    fontSize = Dimens.TextFont12
                ),
                modifier = Modifier.padding(bottom = Dimens.PaddingBottom7)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = Utils.formatExpiryDate(expiryDate),
                    style = MaterialTheme.typography.headlineMedium.copy(
                        color = textColor,
                        fontSize = Dimens.TextFont
                    ),
                    modifier = Modifier.clickable { onExpiryDateClick() }
                )
                Text(
                    text = cardPaySystem,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        color = textColor,
                        fontSize = Dimens.TextFont12
                    ),
                    modifier = Modifier.clickable { onCardPaySystemClick() }
                )
            }
        }
    }
}