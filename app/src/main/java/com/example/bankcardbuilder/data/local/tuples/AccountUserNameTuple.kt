package com.example.bankcardbuilder.data.local.tuples

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class AccountUserNameTuple(
    @ColumnInfo(name = "id") @PrimaryKey val id: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "surname") val surname: String
)