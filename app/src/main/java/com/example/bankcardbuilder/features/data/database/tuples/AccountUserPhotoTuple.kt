package com.example.bankcardbuilder.features.data.database.tuples

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class AccountUserPhotoTuple(
    @ColumnInfo(name = "id") @PrimaryKey val id: Long,
    @ColumnInfo(name = "photo") val photo: String?
)