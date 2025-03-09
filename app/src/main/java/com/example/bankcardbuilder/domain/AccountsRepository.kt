package com.example.bankcardbuilder.domain

import com.example.bankcardbuilder.exeption.CardNotFoundException
import com.example.bankcardbuilder.exeption.EmptyFieldException
import com.example.bankcardbuilder.exeption.InvalidFieldFormatException
import com.example.bankcardbuilder.exeption.InvalidPinCodeException
import com.example.bankcardbuilder.presentation.entity.CardInfo
import com.example.bankcardbuilder.presentation.entity.FullName
import com.example.bankcardbuilder.presentation.entity.SignUpData
import com.example.bankcardbuilder.presentation.screens.mainProfile.ShortCardInfo
import kotlinx.coroutines.flow.Flow

@JvmInline
value class UserId(val id: Long)

interface AccountsRepository {

    /**
     * Create a new account.
     * @throws [AccountAlreadyExistsException]
     * @throws [StorageException]
     */
    suspend fun createAccount(signUpData: SignUpData)

    /**
     * Whether user is signed-in or not.
     */
    suspend fun isSignedIn(): Boolean

    /**
     * Sign-in with the email and password.
     * @throws [EmptyFieldException]
     * @throws [InvalidEmailException]
     * @throws [AuthException]
     */
    suspend fun signIn(email: String, password: CharArray): UserId

    /**
     * Sign-out from the app.
     */
    suspend fun logOut()

    /**
     * Get current account username.
     */
    suspend fun getUserName(): FullName

    /**
     * Set user's card data.
     * @throws [CardNumberAlreadyExistsException]
     * @throws [AuthException]
     * @throws [StorageException]
     */
    suspend fun setCardData(data: CardInfo)

    /**
     * Toggles the lock state of a card.
     * @throws [CardNotFoundException]
     * @throws [AuthException]
     * @throws [StorageException]
     */
    suspend fun toggleCardLock(cardNumber: String, isLocked: Boolean)

    /**
     * Verifies if the provided PIN code is correct for the specified card.
     * @throws [InvalidPinCodeException]
     * @throws [InvalidFieldFormatException]
     */
    suspend fun isPinCodeCorrect(cardNumber: String, pinCode: String): Boolean

    /**
     * Get current account phone number.
     */
    suspend fun getPhoneNumber(): String

    /**
     * Get current account user email.
     */
    suspend fun getUserEmail(): String

    /**
     * Set account photo.
     * @throws [SameDataException]
     * @throws [AuthException]
     * @throws [StorageException]
     */
    suspend fun setAccountPhoto(photo: String)

    /**
     * Get current account photo.
     */
    fun getAccountPhoto(): Flow<String>

    /**
     * Get oll cards from DB for the specified account.
     * @throws [AuthException]
     */
    fun getCardsFlow(): Flow<List<ShortCardInfo>>

}