package com.example.bankcardbuilder.features.domain.entity

data class ShortCardInfo(
    val color: String,
    val paySystem: String,
    val cardNumber: String,
    val isLocked: Boolean
)