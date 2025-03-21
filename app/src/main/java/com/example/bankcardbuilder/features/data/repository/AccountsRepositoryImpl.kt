package com.example.bankcardbuilder.features.data.repository

import android.database.sqlite.SQLiteConstraintException
import android.util.Patterns
import com.example.bankcardbuilder.features.data.database.AccountDbEntity
import com.example.bankcardbuilder.features.data.database.tuples.AccountUserPhotoTuple
import com.example.bankcardbuilder.features.data.database.AccountsDao
import com.example.bankcardbuilder.features.data.database.CardDbEntity
import com.example.bankcardbuilder.features.data.database.CardsDao
import com.example.bankcardbuilder.features.domain.AccountsRepository
import com.example.bankcardbuilder.features.domain.UserId
import com.example.bankcardbuilder.core.domain.AccountAlreadyExistsException
import com.example.bankcardbuilder.core.domain.AuthException
import com.example.bankcardbuilder.core.domain.CardNotFoundException
import com.example.bankcardbuilder.core.domain.CardNumberAlreadyExistsException
import com.example.bankcardbuilder.core.domain.EmptyFieldException
import com.example.bankcardbuilder.core.domain.Field
import com.example.bankcardbuilder.core.domain.InvalidEmailException
import com.example.bankcardbuilder.core.domain.InvalidFieldFormatException
import com.example.bankcardbuilder.core.domain.InvalidPinCodeException
import com.example.bankcardbuilder.core.domain.SameDataException
import com.example.bankcardbuilder.core.domain.wrapSQLiteException
import com.example.bankcardbuilder.features.domain.entity.CardInfo
import com.example.bankcardbuilder.features.domain.entity.FullName
import com.example.bankcardbuilder.features.domain.entity.SignUpData
import com.example.bankcardbuilder.features.domain.entity.ShortCardInfo
import com.example.bankcardbuilder.features.domain.SecurityUtils
import com.example.bankcardbuilder.features.domain.AccountSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AccountsRepositoryImpl @Inject constructor(
    private val securityUtils: SecurityUtils,
    private val accountSettings: AccountSettings,
    private val accountsDao: AccountsDao,
    private val cardsDao: CardsDao
) : AccountsRepository {

    override suspend fun createAccount(signUpData: SignUpData) = wrapSQLiteException {
        try {
            val salt = securityUtils.generateSalt()
            val hash = securityUtils.passwordToHash(signUpData.password, salt)
            signUpData.password.fill('*')
            signUpData.confirmPassword.fill('*')

            accountsDao.createAccount(
                AccountDbEntity(
                    id = 0,
                    name = signUpData.fullName.name,
                    surname = signUpData.fullName.surname,
                    mobileNumber = signUpData.mobileNumber,
                    email = signUpData.email,
                    answer = signUpData.answer,
                    photo = signUpData.photo,
                    hash = securityUtils.bytesToString(hash),
                    salt = securityUtils.bytesToString(salt),
                    createdAt = signUpData.createdAt
                )
            )

            accountSettings.setCurrentUserEmail(signUpData.email)

        } catch (e: SQLiteConstraintException) {
            throw AccountAlreadyExistsException()
        }
    }

    override suspend fun isSignedIn(): Boolean {
        return accountSettings.getCurrentUserEmail() != AccountSettings.NO_ACCOUNT_EMAIL
    }

    override suspend fun signIn(email: String, password: CharArray): UserId {
        if (email.isBlank()) throw EmptyFieldException(Field.EMAIL)
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) throw InvalidEmailException()

        if (password.isEmpty()) throw EmptyFieldException(Field.PASSWORD)

        val accountId: Long = findAccountIdByEmailAndPassword(email, password)

        accountSettings.setCurrentUserEmail(email)
        return UserId(accountId)
    }

    private suspend fun findAccountIdByEmailAndPassword(email: String, password: CharArray): Long {
        val tuple = accountsDao.findByEmail(email) ?: throw AuthException()

        val saltBytes = securityUtils.stringToBytes(tuple.salt)
        val hashBytes = securityUtils.passwordToHash(password, saltBytes)
        val hashString = securityUtils.bytesToString(hashBytes)
        password.fill('*')
        if (tuple.hash != hashString) throw AuthException()
        return tuple.id
    }

    override suspend fun logOut() {
        accountSettings.setCurrentUserEmail(AccountSettings.NO_ACCOUNT_EMAIL)
    }

    override suspend fun getUserName(): FullName {

        val email = accountSettings.getCurrentUserEmail()
        val userName = accountsDao.getUserNameByEmail(email)

        return FullName(
            userName.name,
            userName.surname
        )
    }

    override suspend fun setCardData(data: CardInfo) = wrapSQLiteException {
        val email = accountSettings.getCurrentUserEmail()
        val account = accountsDao.findByEmail(email) ?: throw AuthException()

        val existingCard = cardsDao.findByAccountIdAndCardNumber(account.id, data.cardNumber)

        if (existingCard != null) {
            throw CardNumberAlreadyExistsException()
        }

        val newCard = CardDbEntity(
            id = 0,
            accountId = account.id,
            userName = data.cardNameUser,
            cardNumber = data.cardNumber,
            expiryDate = data.expiryDate,
            cardPaySystem = data.cardPaySystem,
            color = data.cardColor,
            pinCode = data.pinCode,
            isLocked = false
        )
        cardsDao.insertCard(newCard)
    }

    override fun getCardsFlow(): Flow<List<ShortCardInfo>> = flow {
        val email = accountSettings.getCurrentUserEmail()
        val account = accountsDao.findByEmail(email) ?: throw AuthException()

        cardsDao.getCardByAccountId(account.id).map { cardDbEntities ->
            cardDbEntities.map { cardUi ->
                ShortCardInfo(
                    color = cardUi.color,
                    paySystem = cardUi.cardPaySystem,
                    cardNumber = cardUi.cardNumber,
                    isLocked = cardUi.isLocked
                )
            }
        }.collect { cardInfoList ->
            emit(cardInfoList)
        }
    }

    override suspend fun toggleCardLock(cardNumber: String, isLocked: Boolean) =
        wrapSQLiteException {
            val email = accountSettings.getCurrentUserEmail()
            val account = accountsDao.findByEmail(email) ?: throw AuthException()

            val card = cardsDao.findByAccountIdAndCardNumber(account.id, cardNumber)
                ?: throw CardNotFoundException()

            cardsDao.updateCardLockState(card.id, isLocked)
        }

    override suspend fun isPinCodeCorrect(cardNumber: String, pinCode: String): Boolean {
        val isValid = cardsDao.isPinCodeValid(cardNumber, pinCode)
        if (pinCode.isBlank()) throw InvalidFieldFormatException(Field.PINCODE)
        if (!isValid) throw InvalidPinCodeException()

        return true
    }

    override suspend fun getPhoneNumber(): String {
        val email = accountSettings.getCurrentUserEmail()
        val phoneNumber = accountsDao.getUserPhoneNumberByEmail(email)
        return phoneNumber.mobileNumber
    }

    override suspend fun getUserEmail(): String {
        return accountSettings.getCurrentUserEmail()
    }

    override suspend fun setAccountPhoto(photo: String) = wrapSQLiteException {
        val email = accountSettings.getCurrentUserEmail()
        val account = accountsDao.findByEmail(email) ?: throw AuthException()

        val currentPhoto = accountsDao.getAccountPhotoByEmail(email).firstOrNull()?.photo
        if (currentPhoto == photo) throw SameDataException()

        accountsDao.setAccountPhoto(AccountUserPhotoTuple(account.id, photo))
    }

    override fun getAccountPhoto(): Flow<String> {
        val email = accountSettings.getCurrentUserEmail()
        return accountsDao.getAccountPhotoByEmail(email).map {
            it.photo ?: ""
        }
    }
}