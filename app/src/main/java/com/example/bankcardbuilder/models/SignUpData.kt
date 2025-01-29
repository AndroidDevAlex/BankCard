package com.example.bankcardbuilder.models

import android.util.Patterns
import com.example.bankcardbuilder.exeption.EmptyFieldException
import com.example.bankcardbuilder.exeption.Field
import com.example.bankcardbuilder.exeption.InvalidEmailException
import com.example.bankcardbuilder.exeption.InvalidPasswordException
import com.example.bankcardbuilder.exeption.PasswordMismatchException

data class SignUpData(
    val email: String,
    val password: CharArray,
    val confirmPassword: CharArray
) {

    fun validate() {
        if (email.isBlank()) throw EmptyFieldException(Field.EMAIL)
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) throw InvalidEmailException()
        if (password.isEmpty()) throw EmptyFieldException(Field.PASSWORD)
        if (!password.contentEquals(confirmPassword)) throw PasswordMismatchException()
        if (password.size < 8 || !password.any { it.isDigit() } || !password.any { it.isUpperCase() }) {
            throw InvalidPasswordException()
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SignUpData

        if (email != other.email) return false
        if (!password.contentEquals(other.password)) return false
        return confirmPassword.contentEquals(other.confirmPassword)
    }

    override fun hashCode(): Int {
        var result = email.hashCode()
        result = 31 * result + password.contentHashCode()
        result = 31 * result + confirmPassword.contentHashCode()
        return result
    }
}