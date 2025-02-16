package com.example.bankcardbuilder.dataBase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.bankcardbuilder.dataBase.tuples.AccountSignInTuple
import com.example.bankcardbuilder.dataBase.tuples.AccountUserNameTuple
import com.example.bankcardbuilder.dataBase.tuples.AccountUserPhoneNumberTuple
import com.example.bankcardbuilder.dataBase.tuples.AccountUserPhotoTuple
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountsDao {
    @Insert(entity = AccountDbEntity::class)
    suspend fun createAccount(accountDbEntity: AccountDbEntity)

    @Query("SELECT id, hash, salt FROM accounts WHERE email = :email")
    suspend fun findByEmail(email: String): AccountSignInTuple?

    @Query("SELECT id, mobileNumber FROM accounts WHERE email = :email")
    suspend fun getUserPhoneNumberByEmail(email: String): AccountUserPhoneNumberTuple

    @Query("SELECT id, name, surname FROM accounts WHERE email = :email")
    suspend fun getUserNameByEmail(email: String): AccountUserNameTuple

    @Update(entity = AccountDbEntity::class)
    suspend fun setAccountPhoto(photo: AccountUserPhotoTuple)

    @Query("SELECT id, photo FROM accounts WHERE email = :email")
    fun getAccountPhotoByEmail(email: String): Flow<AccountUserPhotoTuple>
}