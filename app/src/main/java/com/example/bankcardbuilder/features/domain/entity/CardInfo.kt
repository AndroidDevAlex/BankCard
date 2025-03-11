package com.example.bankcardbuilder.features.domain.entity

data class CardInfo(
    val cardNumber: String,
    val cardNameUser: String,
    val expiryDate: String,
    val cardPaySystem: String,
    val cardColor: String,
    val pinCode: String
)