package com.example.bankcardbuilder.screens.settingsCard.mainCardSettings

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import com.example.bankcardbuilder.R
import com.example.bankcardbuilder.screens.settingsCard.mainCardSettings.InputType.*
import com.example.bankcardbuilder.ui.theme.ButtonDialog
import com.example.bankcardbuilder.util.Dimens

@Composable
fun CardSettingsDialog(
    title: String,
    value: String,
    onValueChanged: (String) -> Unit,
    onDismiss: () -> Unit,
    inputType: InputType
) {
    var text by remember { mutableStateOf(value) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
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
                visualTransformation = when (inputType) {
                    DATE -> ExpiryDateTransformation()
                    NUMBER -> CardNumberTransformation()
                    else -> VisualTransformation.None
                },
                label = { Text(title) }
            )
        },
        confirmButton = {
            Button(
                onClick = {
                    onValueChanged(text)
                    onDismiss()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = ButtonDialog,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(Dimens.ButCornerShape)
            ) {
                Text(stringResource(R.string.confirm))
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(
                    containerColor = ButtonDialog,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(Dimens.ButCornerShape)
            ) {
                Text(stringResource(R.string.cancel))
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