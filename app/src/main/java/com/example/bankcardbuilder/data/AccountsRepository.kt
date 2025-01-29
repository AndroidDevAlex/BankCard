package com.example.bankcardbuilder.data

import com.example.bankcardbuilder.exeption.CardNotFoundException
import com.example.bankcardbuilder.exeption.EmptyFieldException
import com.example.bankcardbuilder.exeption.InvalidFieldFormatException
import com.example.bankcardbuilder.exeption.InvalidPinCodeException
import com.example.bankcardbuilder.models.CardInfo
import com.example.bankcardbuilder.models.FullName
import com.example.bankcardbuilder.models.SignUpData
import com.example.bankcardbuilder.navigation.ScreenState
import com.example.bankcardbuilder.screens.settingsAccount.main.ShortCardInfo
import kotlinx.coroutines.flow.Flow

@JvmInline
value class UserId(val id: Long)

interface AccountsRepository {

    /**
     * Create a new account.
     * @throws [EmptyFieldException]
     * @throws [InvalidEmailException]
     * @throws [PasswordMismatchException]
     * @throws [AccountAlreadyExistsException]
     * @throws [InvalidPasswordException]
     * @throws [StorageException]
     */
    suspend fun signUp(signUpData: SignUpData)

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
     * Set user's name and surname.
     * @throws [EmptyFieldException]
     * @throws [InvalidFieldFormatException]
     * @throws [AuthException]
     * @throws [StorageException]
     */
    suspend fun setUserName(fullName: FullName)

    /**
     * Set user's card data.
     * @throws [CardNumberAlreadyExistsException]
     * @throws [InvalidFieldFormatException]
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
     * Set user's phone number.
     * @throws [AuthException]
     * @throws [StorageException]
     */
    suspend fun setPhoneNumber(phoneNumber: String)

    /**
     * Sets PIN code for the specified card.
     * @throws [InvalidFieldFormatException]
     * @throws [StorageException]
     */
    suspend fun setPinCode(pinCode: String, cardNumber: String)

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
     * Set the answer to the security question.
     * @throws [EmptyFieldException]
     * @throws [AuthException]
     * @throws [InvalidFieldFormatException]
     * @throws [StorageException]
     */
    suspend fun setAccountAnswer(answer: String)

    /**
     * Get oll cards from DB for the specified account.
     * @throws [AuthException]
     */
    fun getCardsFlow(): Flow<List<ShortCardInfo>>

    /**
     * Get current screen state.
     */
    suspend fun getCurrentScreenState(): ScreenState
}