package com.example.bankcardbuilder.dataBase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "accounts",
    indices = [
        Index("email", unique = true)
    ]
)

data class AccountDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String,
    val surname: String,
    val mobileNumber: String,
    val answer: String,
    @ColumnInfo(collate = ColumnInfo.NOCASE) val email: String,
    val photo: String?,
    val hash: String,
    @ColumnInfo(name = "salt", defaultValue = "") val salt: String,
    @ColumnInfo(name = "created_at") val createdAt: Long
)