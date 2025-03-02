package com.example.bankcardbuilder.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.bankcardbuilder.R
import com.example.bankcardbuilder.screens.registration.PhoneNumberVisualTransformation
import com.example.bankcardbuilder.ui.theme.Gray
import com.example.bankcardbuilder.ui.theme.GrayInf
import com.example.bankcardbuilder.util.Dimens

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isError: Boolean = false,
    errorMessage: String? = null,
    isPassword: Boolean = false,
    isPasswordVisible: Boolean = false,
    onTogglePasswordVisibility: (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isPhoneNumber: Boolean = false
) {
    val isFocused = remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        val labelColor = when {
            isError -> Color.Red
            value.isNotEmpty() -> colorResource(id = R.color.orange)
            else -> Gray
        }

        val underlineColor = when {
            isError -> Color.Red
            value.isNotEmpty() -> colorResource(id = R.color.orange)
            else -> GrayInf
        }

        val textColor = if (value.isEmpty()) Color.Gray else MaterialTheme.colorScheme.onSurface

        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = Dimens.TextFont12),
            color = labelColor,
            modifier = Modifier.padding(bottom = if (label == "Password") Dimens.PaddingTex else Dimens.PaddingTex24)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = Dimens.PaddingBottom10)
                .drawBehind {
                    val strokeWidth = Dimens.Border.toPx()
                    drawLine(
                        color = underlineColor,
                        start = Offset(0f, size.height),
                        end = Offset(size.width, size.height),
                        strokeWidth = strokeWidth
                    )
                }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                BasicTextField(
                    value = value,
                    onValueChange = { newValue ->
                        if (isPhoneNumber) {
                            val filteredValue = newValue.filter { it.isDigit() }.take(15)
                            onValueChange(filteredValue)
                        } else {
                            onValueChange(newValue)
                        }
                    },
                    textStyle = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = Dimens.TextFont15,
                        color = textColor
                    ),
                    cursorBrush = SolidColor(colorResource(id = R.color.orange)),
                    visualTransformation = when {
                        isPassword && !isPasswordVisible -> PasswordVisualTransformation()
                        isPhoneNumber -> PhoneNumberVisualTransformation()
                        else -> VisualTransformation.None
                    },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .padding(bottom = if (label == "Password") Dimens.PaddingRow else Dimens.PaddingRow18)
                        .onFocusChanged { isFocused.value = it.isFocused }
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(bottom = Dimens.RowPaddingBottom)
                ) {
                    trailingIcon?.invoke()

                    if (isPassword && onTogglePasswordVisibility != null) {
                        IconButton(
                            onClick = onTogglePasswordVisibility,
                            modifier = Modifier.size(Dimens.IconSizeDp)
                        ) {
                            Icon(
                                painter = painterResource(id = if (isPasswordVisible) R.drawable.visibility else R.drawable.visibility_off),
                                contentDescription = "Toggle Password Visibility",
                                tint = if (isPasswordVisible) colorResource(id = R.color.orange) else Gray
                            )
                        }
                    }
                }
            }
        }

        if (isError && !errorMessage.isNullOrEmpty()) {
            Text(
                text = errorMessage,
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = Dimens.TextFont12),
                color = Color.Red,
                modifier = Modifier.padding(top = Dimens.TexPadding)
            )
        }
    }
}