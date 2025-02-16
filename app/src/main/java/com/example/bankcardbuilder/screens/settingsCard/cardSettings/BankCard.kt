package com.example.bankcardbuilder.screens.settingsCard.cardSettings

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.Color
import com.example.bankcardbuilder.util.Dimens
import com.example.bankcardbuilder.util.Utils

@Composable
fun BankCard(
    color: Color,
    cardName: String,
    cardNumber: String,
    expiryDate: String,
    cardCompany: String,
    textColor: Color,
    onCardNameClick: () -> Unit,
    onCardNumberClick: () -> Unit,
    onCardCompanyClick: () -> Unit,
    onExpiryDateClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(Dimens.BoxSizeWidth, Dimens.BoxSizeHeight)
            .background(color, shape = RoundedCornerShape(Dimens.CornerShape))
            .border(Dimens.BoxBorder, Color.Black, shape = RoundedCornerShape(Dimens.CornerShape))
    ) {
        Column(modifier = Modifier.padding(Dimens.PaddingColumn)) {
            Text(
                text = cardName,
                style = MaterialTheme.typography.titleMedium.copy(color = textColor),
                modifier = Modifier
                    .padding(bottom = Dimens.PaddingTextBottom)
                    .clickable {
                        onCardNameClick()
                    }
            )
            Text(
                text = Utils.formatCardNumber(cardNumber),
                style = MaterialTheme.typography.bodyMedium.copy(color = textColor),
                modifier = Modifier
                    .padding(bottom = Dimens.PaddingTextBot)
                    .clickable { onCardNumberClick() }
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = Utils.formatExpiryDate(expiryDate),
                    style = MaterialTheme.typography.bodySmall.copy(color = textColor),
                    modifier = Modifier.clickable { onExpiryDateClick() }
                )
                Text(
                    text = cardCompany,
                    style = MaterialTheme.typography.bodySmall.copy(color = textColor),
                    modifier = Modifier.clickable { onCardCompanyClick() }
                )
            }
        }
    }
}