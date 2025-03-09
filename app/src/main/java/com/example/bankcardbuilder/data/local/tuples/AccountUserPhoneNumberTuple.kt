package com.example.bankcardbuilder.data.local.tuples

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class AccountUserPhoneNumberTuple(
    @ColumnInfo(name = "id") @PrimaryKey val id: Long,
    @ColumnInfo(name = "mobileNumber") val mobileNumber: String
)