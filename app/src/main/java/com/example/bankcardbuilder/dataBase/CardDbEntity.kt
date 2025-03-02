package com.example.bankcardbuilder.dataBase

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "cards",
    indices = [
        Index("accountId")
    ]
)

data class CardDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val accountId: Long,
    val userName: String,
    val cardNumber: String,
    val expiryDate: String,
    val cardPaySystem: String,
    val color: String,
    val pinCode: String,
    val isLocked: Boolean
)