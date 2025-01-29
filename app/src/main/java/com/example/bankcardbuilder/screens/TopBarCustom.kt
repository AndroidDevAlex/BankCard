package com.example.bankcardbuilder.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.bankcardbuilder.R
import com.example.bankcardbuilder.util.Dimens

@Composable
fun TopBarCustom(
    title: String? = null,
    onBackClicked: (() -> Unit)? = null,
    onMenuClicked: (() -> Unit)? = null,
    onExitClicked: (() -> Unit)? = null,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = Dimens.PaddingRowTop, start = Dimens.PaddingRow, end = Dimens.PaddingRow),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        onMenuClicked?.let {
            IconButton(
                onClick = it
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu",
                    tint = Color.Black,
                    modifier = Modifier.size(Dimens.IconSize)
                )
            }
        }

        onExitClicked?.let {
            IconButton(
                onClick = it
            ) {
                Icon(
                    imageVector = Icons.Default.ExitToApp,
                    contentDescription = "Exit",
                    tint = Color.Black,
                    modifier = Modifier
                        .size(Dimens.IconSize)
                )
            }
        }

        onBackClicked?.let {
            IconButton(
                onClick = it,
                modifier = Modifier
                    .width(Dimens.IconWidth)
                    .height(Dimens.IconHeight)
                    .background(
                        color = colorResource(id = R.color.orange),
                        shape = RoundedCornerShape(Dimens.IconCornerShape)
                    )
                    .padding(Dimens.IconPadding)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier.size(Dimens.IconSizeBack)
                )
            }
        }

        title?.let {
            Text(
                text = it,
                color = Color.Black,
                fontSize = Dimens.TextFontSize,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
        }
    }
}