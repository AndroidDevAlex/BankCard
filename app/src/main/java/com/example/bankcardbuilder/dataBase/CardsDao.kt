package com.example.bankcardbuilder.dataBase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CardsDao {

    @Insert(entity = CardDbEntity::class)
    suspend fun insertCard(card: CardDbEntity)

    @Query("SELECT * FROM cards WHERE accountId = :accountId")
    fun getCardByAccountId(accountId: Long): Flow<List<CardDbEntity>>

    @Query("SELECT * FROM cards WHERE accountId = :accountId AND cardNumber = :cardNumber LIMIT 1")
    suspend fun findByAccountIdAndCardNumber(accountId: Long, cardNumber: String): CardDbEntity?

    @Query("UPDATE cards SET isLocked = :isLocked WHERE id = :cardId")
    suspend fun updateCardLockState(cardId: Long, isLocked: Boolean)

    @Query("SELECT COUNT(*) > 0 FROM cards WHERE cardNumber = :cardNumber AND pinCode = :pinCode")
    suspend fun isPinCodeValid(cardNumber: String, pinCode: String): Boolean
}