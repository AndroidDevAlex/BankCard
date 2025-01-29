package com.example.bankcardbuilder.exeption

import android.database.sqlite.SQLiteException

suspend fun <T> wrapSQLiteException(block: suspend () -> T): T {
    try {
        return block()
    } catch (e: SQLiteException) {
        val appException = StorageException()
        appException.initCause(e)
        throw appException
    }
}