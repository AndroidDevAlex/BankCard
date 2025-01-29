package com.example.bankcardbuilder.screens.settingsCard

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.bankcardbuilder.R
import com.example.bankcardbuilder.util.Dimens

@Composable
fun PinCodeKeyboard(
    pinCode: String,
    onPinCodeChange: (String) -> Unit
) {
    val digits = (1..9).toList() + listOf(0)

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        horizontalArrangement = Arrangement.spacedBy(Dimens.HorizontalSpacedBy),
        verticalArrangement = Arrangement.spacedBy(Dimens.VerticalSpacedBy),
        modifier = Modifier.padding(start = Dimens.PaddingStart, end = Dimens.PaddingEnd),
        content = {
            items(digits.size) { index ->
                val number = digits[index]
                val isSelected = pinCode.contains(number.toString())

                Box(
                    modifier = Modifier
                        .size(Dimens.BoxSize)
                        .clip(CircleShape)
                        .background(
                            if (isSelected) colorResource(id = R.color.orange) else Color.White,
                            shape = CircleShape
                        )
                        .border(
                            Dimens.BoxBorder,
                            if (isSelected) colorResource(id = R.color.orange) else Color.Black,
                            CircleShape
                        )
                        .clickable {
                            if (pinCode.length < 5) {
                                onPinCodeChange(pinCode + number.toString())
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = number.toString(),
                        style = MaterialTheme.typography.titleMedium,
                        color = if (isSelected) Color.White else Color.Black
                    )
                }
            }
            item {
                Box(
                    modifier = Modifier
                        .size(Dimens.BoxSize)
                        .clip(CircleShape)
                        .background(colorResource(id = R.color.red), shape = CircleShape)
                        .border(Dimens.BoxBorder, Color.Black, CircleShape)
                        .clickable {
                            if (pinCode.isNotEmpty()) {
                                onPinCodeChange(pinCode.dropLast(1))
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.delete),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = Dimens.BoxText
                        ),
                        color = Color.Black
                    )
                }
            }
        }
    )
}