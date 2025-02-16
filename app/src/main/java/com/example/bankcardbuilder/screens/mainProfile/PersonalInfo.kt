package com.example.bankcardbuilder.screens.mainProfile

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.example.bankcardbuilder.R
import com.example.bankcardbuilder.ui.theme.Orange
import com.example.bankcardbuilder.util.Dimens

@Composable
fun PersonalInfo(label: String, value: String?) {
    Box(
        modifier = Modifier
            .height(Dimens.BoxSize50)
            .clip(RoundedCornerShape(Dimens.IconCornerShape))
            .background(colorResource(id = R.color.light_gray))
            .padding(horizontal = Dimens.BoxPaddingHorizontal)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "$label ",
                style = MaterialTheme.typography.bodySmall.copy(fontSize = Dimens.FontSizeRow),
                color = Orange
            )

            Text(
                text = value ?: "",
                style = MaterialTheme.typography.bodySmall.copy(fontSize = Dimens.RowFontSize),
                color = Color.Black
            )
        }
    }
}