package com.example.bankcardbuilder.screens.registration

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import com.example.bankcardbuilder.util.Utils

internal class PhoneNumberVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val formattedText = Utils.formatPhoneNumber(text.text)
        return TransformedText(
            AnnotatedString(formattedText),
            PhoneNumberOffsetMapping(text.text)
        )
    }
}

private class PhoneNumberOffsetMapping(private val originalText: String) : OffsetMapping {
    override fun originalToTransformed(offset: Int): Int {
        val transformed = Utils.formatPhoneNumber(originalText)
        var transformedOffset = 0
        var originalOffset = 0

        while (originalOffset < offset && transformedOffset < transformed.length) {
            if (transformed[transformedOffset].isDigit() || transformed[transformedOffset] == '+') {
                originalOffset++
            }
            transformedOffset++
        }

        while (transformedOffset < transformed.length && !transformed[transformedOffset].isDigit()) {
            transformedOffset++
        }

        return transformedOffset
    }

    override fun transformedToOriginal(offset: Int): Int {
        val transformed = Utils.formatPhoneNumber(originalText)
        var transformedOffset = 0
        var originalOffset = 0

        while (transformedOffset < offset && originalOffset < originalText.length) {
            if (transformed[transformedOffset].isDigit() || transformed[transformedOffset] == '+') {
                originalOffset++
            }
            transformedOffset++
        }

        while (transformedOffset < transformed.length && !transformed[transformedOffset].isDigit()) {
            transformedOffset++
        }

        return originalOffset
    }
}