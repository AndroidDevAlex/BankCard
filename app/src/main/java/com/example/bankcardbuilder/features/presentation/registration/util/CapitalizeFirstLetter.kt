package com.example.bankcardbuilder.features.presentation.registration.util

fun String.capitalizeFirstLetter(): String {
    return this.lowercase()
        .split(" ")
        .joinToString(" ") { word ->
            word.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
        }
}