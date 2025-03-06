package com.example.bankcardbuilder.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import com.example.bankcardbuilder.util.Dimens

@Composable
fun TopBarCustom(
    title: String? = null,
    onBackClicked: (() -> Unit)? = null,
    onMenuClicked: (() -> Unit)? = null,
    onExitClicked: (() -> Unit)? = null,
    spacerWidth: Dp = Dimens.SpacerWidth58,
    start: Dp = Dimens.Start35,
    end: Dp = Dimens.End35

) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = Dimens.PaddingRowTop, start = start, end = end)
    ) {
        onMenuClicked?.let {
            IconButton(
                onClick = it,
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu",
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.size(Dimens.IconSize)
                )
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            onBackClicked?.let {
                IconButton(
                    onClick = it,
                    modifier = Modifier
                        .width(Dimens.SpacerWidth70)
                        .height(Dimens.SpacerHeight41)
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(Dimens.BoxCornerShape)
                        )
                        .padding(Dimens.IconPadding)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier.size(Dimens.IconSize21)
                    )
                }
            }

            Spacer(modifier = Modifier.width(spacerWidth))

            title?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontSize = Dimens.TextFontSize
                    ),

                    textAlign = TextAlign.Center
                )
            }
        }

        onExitClicked?.let {
            IconButton(
                onClick = it,
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Icon(
                    imageVector = Icons.Default.ExitToApp,
                    contentDescription = "Exit",
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.size(Dimens.IconSize)
                )
            }
        }
    }
}