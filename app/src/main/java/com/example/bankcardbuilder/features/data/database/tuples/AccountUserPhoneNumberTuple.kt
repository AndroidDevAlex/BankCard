package com.example.bankcardbuilder.features.data.database.tuples

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class AccountUserPhoneNumberTuple(
    @ColumnInfo(name = "id") @PrimaryKey val id: Long,
    @ColumnInfo(name = "mobileNumber") val mobileNumber: String
)