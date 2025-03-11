package com.example.bankcardbuilder.features.presentation.settingsCard.cardSettings.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import com.example.bankcardbuilder.R
import com.example.bankcardbuilder.features.presentation.settingsCard.cardSettings.utils.InputType.*
import com.example.bankcardbuilder.core.util.Dimens

@Composable
fun CardSettingsDialog(
    title: String,
    onValueChanged: (String) -> Unit,
    onDismiss: () -> Unit,
    inputType: InputType
) {
    var text by remember { mutableStateOf("") }

    val keyboardOptions = when (inputType) {
        DATE -> KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        )
        NUMBER -> KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        )
        else -> KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        )
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.onPrimary,
        title = {
            Text(
                title,
                style = MaterialTheme.typography.headlineMedium.copy(fontSize = Dimens.TextFontSize),
                color = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        text = {
            TextField(
                value = text,
                onValueChange = {
                    text = when (inputType) {
                        DATE -> it.replace(Regex("[^0-9]"), "").take(4)
                        NUMBER -> it.replace(Regex("[^0-9]"), "").take(16)
                        NAME -> it.trimStart('-').replace("[^a-zA-Z ]".toRegex(), "").take(30)
                        else -> it.trimStart('-').replace("[^a-zA-Z0-9 ]".toRegex(), "").take(12)
                    }
                },
                textStyle = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = Dimens.TextFont,
                    color = MaterialTheme.colorScheme.onSecondary
                ),
                placeholder = {
                    Text(
                        when (inputType) {
                            NUMBER -> "Card Number"
                            NAME -> "Your Name"
                            DATE -> "MM/YY"
                            else -> "Payment System"
                        },
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = Dimens.TextFontSp,
                            color = MaterialTheme.colorScheme.onTertiaryContainer
                        )
                    )
                },
                visualTransformation = when (inputType) {
                    DATE -> ExpiryDateTransformation()
                    NUMBER -> CardNumberTransformation()
                    else -> VisualTransformation.None
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface
                ),
                keyboardOptions = keyboardOptions
            )

        },
        confirmButton = {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.tertiary
                    ),
                    shape = RoundedCornerShape(Dimens.ButCornerShape),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        stringResource(R.string.cancel),
                        style = MaterialTheme.typography.bodyLarge.copy(fontSize = Dimens.TextFont15)
                    )
                }

                Spacer(modifier = Modifier.width(Dimens.SpacerWidth10))

                Button(
                    onClick = {
                        onValueChanged(text)
                        onDismiss()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.tertiary
                    ),
                    shape = RoundedCornerShape(Dimens.ButCornerShape),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        stringResource(R.string.confirm),
                        style = MaterialTheme.typography.bodyLarge.copy(fontSize = Dimens.FontSize15)
                    )
                }
            }
        }
    )
}

private class ExpiryDateTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = text.text.take(4)
        val formatted = buildString {
            for (i in trimmed.indices) {
                if (i == 2) append("/")
                append(trimmed[i])
            }
        }

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                return when {
                    offset <= 2 -> offset
                    offset in 3..4 -> offset + 1
                    else -> formatted.length
                }
            }

            override fun transformedToOriginal(offset: Int): Int {
                return when {
                    offset <= 2 -> offset
                    offset in 3..5 -> offset - 1
                    else -> trimmed.length
                }
            }
        }

        return TransformedText(AnnotatedString(formatted), offsetMapping)
    }
}

private class CardNumberTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = text.text.take(16)
        val formatted = buildString {
            for (i in trimmed.indices) {
                if (i > 0 && i % 4 == 0) append(" ")
                append(trimmed[i])
            }
        }

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                return when (offset) {
                    0 -> 0
                    in 1..16 -> offset + (offset - 1) / 4
                    else -> formatted.length
                }
            }

            override fun transformedToOriginal(offset: Int): Int {
                return when (offset) {
                    0 -> 0
                    in 1..formatted.length -> offset - (offset - 1) / 5
                    else -> trimmed.length
                }
            }
        }

        return TransformedText(AnnotatedString(formatted), offsetMapping)
    }
}