package com.example.bankcardbuilder.exeption

abstract class AppException : RuntimeException()

/**
 * If unable to execute SQL query.
 */
class StorageException : AppException()

/**
 * User is not authorized.
 */
class AuthException : AppException()