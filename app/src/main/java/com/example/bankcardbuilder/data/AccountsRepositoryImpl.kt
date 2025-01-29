package com.example.bankcardbuilder.data

import android.database.sqlite.SQLiteConstraintException
import android.util.Patterns
import com.example.bankcardbuilder.dataBase.AccountDbEntity
import com.example.bankcardbuilder.dataBase.tuples.AccountUserAnswerTuple
import com.example.bankcardbuilder.dataBase.tuples.AccountUserNameTuple
import com.example.bankcardbuilder.dataBase.tuples.AccountUserPhoneNumberTuple
import com.example.bankcardbuilder.dataBase.tuples.AccountUserPhotoTuple
import com.example.bankcardbuilder.dataBase.AccountsDao
import com.example.bankcardbuilder.dataBase.CardDbEntity
import com.example.bankcardbuilder.dataBase.CardsDao
import com.example.bankcardbuilder.exeption.AccountAlreadyExistsException
import com.example.bankcardbuilder.exeption.AuthException
import com.example.bankcardbuilder.exeption.CardNotFoundException
import com.example.bankcardbuilder.exeption.CardNumberAlreadyExistsException
import com.example.bankcardbuilder.exeption.EmptyFieldException
import com.example.bankcardbuilder.exeption.Field
import com.example.bankcardbuilder.exeption.InvalidEmailException
import com.example.bankcardbuilder.exeption.InvalidFieldFormatException
import com.example.bankcardbuilder.exeption.InvalidPinCodeException
import com.example.bankcardbuilder.exeption.SameDataException
import com.example.bankcardbuilder.exeption.wrapSQLiteException
import com.example.bankcardbuilder.models.CardInfo
import com.example.bankcardbuilder.models.FullName
import com.example.bankcardbuilder.models.SignUpData
import com.example.bankcardbuilder.navigation.NavigationSettings
import com.example.bankcardbuilder.navigation.ScreenState
import com.example.bankcardbuilder.screens.settingsAccount.main.ShortCardInfo
import com.example.bankcardbuilder.security.SecurityUtils
import com.example.bankcardbuilder.settings.AccountSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AccountsRepositoryImpl @Inject constructor(
    private val securityUtils: SecurityUtils,
    private val accountSettings: AccountSettings,
    private val navigationSettings: NavigationSettings,
    private val accountsDao: AccountsDao,
    private val cardsDao: CardsDao
) : AccountsRepository {

    override suspend fun signUp(signUpData: SignUpData) {
        signUpData.validate()
        createAccount(signUpData)
    }

    private suspend fun createAccount(signUpData: SignUpData) = wrapSQLiteException {
        try {
            val salt = securityUtils.generateSalt()
            val hash = securityUtils.passwordToHash(signUpData.password, salt)
            signUpData.password.fill('*')
            signUpData.confirmPassword.fill('*')

            accountsDao.createAccount(
                AccountDbEntity(
                    id = 0,
                    name = null,
                    surname = null,
                    mobileNumber = null,
                    email = signUpData.email,
                    answer = null,
                    photo = null,
                    hash = securityUtils.bytesToString(hash),
                    salt = securityUtils.bytesToString(salt),
                    createdAt = System.currentTimeMillis()
                )
            )

            accountSettings.setCurrentUserEmail(signUpData.email)
            navigationSettings.saveCurrentScreen(ScreenState.SignUp)

        } catch (e: SQLiteConstraintException) {
            throw AccountAlreadyExistsException()
        }
    }

    override suspend fun isSignedIn(): Boolean {
        return accountSettings.getCurrentUserEmail() != AccountSettings.NO_ACCOUNT_EMAIL
    }

    override suspend fun getCurrentScreenState(): ScreenState {
        return navigationSettings.getCurrentScreen()
    }

    override suspend fun signIn(email: String, password: CharArray): UserId {
        if (email.isBlank()) throw EmptyFieldException(Field.EMAIL)
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) throw InvalidEmailException()

        if (password.isEmpty()) throw EmptyFieldException(Field.PASSWORD)

        val accountId: Long = findAccountIdByEmailAndPassword(email, password)

        accountSettings.setCurrentUserEmail(email)
        navigationSettings.saveCurrentScreen(ScreenState.LogIn)
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

    override suspend fun setUserName(fullName: FullName) = wrapSQLiteException {
        val (name, surname) = fullName

        if (name.isBlank()) throw EmptyFieldException(Field.NAME)
        if (surname.isBlank()) throw EmptyFieldException(Field.LASTNAME)

        val correctedName = name.capitalizeFirstLetter()
        val correctedSurname = surname.capitalizeFirstLetter()

        if (name != correctedName) throw InvalidFieldFormatException(Field.NAME)
        if (surname != correctedSurname) throw InvalidFieldFormatException(Field.LASTNAME)

        val email = accountSettings.getCurrentUserEmail()
        val account = accountsDao.findByEmail(email) ?: throw AuthException()

        accountsDao.setUserName(AccountUserNameTuple(account.id, name, surname))

        navigationSettings.saveCurrentScreen(ScreenState.Profile)
    }

    override suspend fun setCardData(data: CardInfo) = wrapSQLiteException {
        val email = accountSettings.getCurrentUserEmail()
        val account = accountsDao.findByEmail(email) ?: throw AuthException()

        if (data.cardNameUser.contains('*')) throw InvalidFieldFormatException(Field.NAME)

        if (data.cardNumber.contains('*') || data.cardNumber.length != 16) throw InvalidFieldFormatException(Field.CARDNUMBER)

        if (data.expiryDate.contains('*')|| data.expiryDate.length != 4) throw InvalidFieldFormatException(Field.EXPIRYDATE)

        if (data.cardCompany.contains('*')) throw InvalidFieldFormatException(Field.CARDCOMPANY)

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
            cardCompany = data.cardCompany,
            color = data.cardColor,
            pinCode = null,
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
                    company = cardUi.cardCompany,
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

    override suspend fun setPhoneNumber(phoneNumber: String) = wrapSQLiteException {
        val email = accountSettings.getCurrentUserEmail()
        val account = accountsDao.findByEmail(email) ?: throw AuthException()
        accountsDao.setUserPhoneNumber(AccountUserPhoneNumberTuple(account.id, phoneNumber))

        navigationSettings.saveCurrentScreen(ScreenState.PhoneNumber)
    }

    override suspend fun setPinCode(pinCode: String, cardNumber: String) = wrapSQLiteException {
        if (!pinCode.matches(Regex("\\d{5}"))) throw InvalidFieldFormatException(Field.PINCODE)

        cardsDao.setPinCodeByCardNumber(cardNumber, pinCode)
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

    override suspend fun setAccountAnswer(answer: String) = wrapSQLiteException {
        if (answer.isBlank()) throw EmptyFieldException(Field.ANSWER)
        val correctedAnswer = answer.capitalizeFirstLetter()

        if (answer != correctedAnswer) throw InvalidFieldFormatException(Field.ANSWER)

        val email = accountSettings.getCurrentUserEmail()
        val account = accountsDao.findByEmail(email) ?: throw AuthException()
        accountsDao.setAccountAnswer(AccountUserAnswerTuple(account.id, answer))

        navigationSettings.saveCurrentScreen(ScreenState.SecurityQuestion)
    }
}