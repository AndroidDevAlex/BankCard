package com.example.bankcardbuilder.util

import androidx.compose.ui.graphics.Color

object Utils {

    fun formatCardNumber(input: String): String {
        val sanitizedInput = input.replace(" ", "")
        val stars = "*".repeat(16 - sanitizedInput.length)
        return (sanitizedInput + stars)
            .chunked(4)
            .joinToString(" ")
    }

    fun formatExpiryDate(input: String): String {
        val sanitized = input.replace(Regex("[^0-9]"), "").take(4)
        val month = sanitized.take(2).padEnd(2, '*')
        val year = sanitized.drop(2).padEnd(2, '*')
        return "$month/$year"
    }

     fun formatPhoneNumber(phoneNumber: String): String {
        val onlyDigit = phoneNumber.filter { it.isDigit() }
        val length = onlyDigit.length
        return when {
            length <= 3 -> "+$onlyDigit"
            length <= 5 -> "+${onlyDigit.substring(0, 3)} ${onlyDigit.substring(3)}"
            length <= 7 -> "+${onlyDigit.substring(0, 3)} ${onlyDigit.substring(3, 5)} ${onlyDigit.substring(5)}"
            length <= 10 -> "+${onlyDigit.substring(0, 3)} ${onlyDigit.substring(3, 5)} ${onlyDigit.substring(5, 8)} ${onlyDigit.substring(8)}"
            length <= 15 -> "+${onlyDigit.substring(0, 3)} ${onlyDigit.substring(3, 5)} ${onlyDigit.substring(5, 8)} ${onlyDigit.substring(8, 11)} ${onlyDigit.substring(11)}"
            else -> onlyDigit
        }
    }

   fun toHex(color: Color): String {
       return String.format("#%02X%02X%02X", (color.red * 255).toInt(), (color.green * 255).toInt(), (color.blue * 255).toInt())
   }
}