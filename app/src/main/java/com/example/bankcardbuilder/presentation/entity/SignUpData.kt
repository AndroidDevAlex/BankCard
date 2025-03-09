package com.example.bankcardbuilder.presentation.entity

data class SignUpData(
    val email: String,
    val password: CharArray,
    val confirmPassword: CharArray,
    val fullName: FullName,
    val mobileNumber: String,
    val answer: String,
    val photo: String?,
    val createdAt: Long
) {

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