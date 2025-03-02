package com.example.bankcardbuilder.exeption

enum class Field {
    EMAIL,
    PASSWORD,
    NAME,
    LASTNAME,
    ANSWER,
    CARDNUMBER,
    EXPIRYDATE,
    CARDPAYSYSTEM,
    PHONENUMBER,
    PINCODE;

    fun displayName(): String {
        return name.lowercase().replaceFirstChar { it.uppercase() }
    }
}

class EmptyFieldException(val field: Field) : AppException()

class PasswordMismatchException : AppException()

class InvalidPasswordException : AppException()

class InvalidFieldFormatException(val field: Field) : AppException()

class InvalidFieldException(val field: Field) : AppException()

class CardNotFoundException : AppException()

class InvalidPinCodeException : AppException()

class CardNumberAlreadyExistsException : AppException()

class InvalidEmailException : AppException()

class AccountAlreadyExistsException : AppException()

class SameDataException : AppException()