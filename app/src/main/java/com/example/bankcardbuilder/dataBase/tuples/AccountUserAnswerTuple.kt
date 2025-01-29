package com.example.bankcardbuilder.dataBase.tuples

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class AccountUserAnswerTuple(
    @ColumnInfo(name = "id") @PrimaryKey val id: Long,
    @ColumnInfo(name = "answer") val answer: String
)