package com.example.bankcardbuilder.dataBase.tuples

import androidx.room.ColumnInfo

data class AccountSignInTuple(
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "hash") val hash: String,
    @ColumnInfo(name = "salt") val salt: String
)