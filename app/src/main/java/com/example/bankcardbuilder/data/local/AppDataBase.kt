package com.example.bankcardbuilder.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    version = 4,
    entities = [
        AccountDbEntity::class,
        CardDbEntity::class
    ]
)

abstract class AppDataBase : RoomDatabase() {

    /**
     * changed field pinCode from NULL to NOT NULL in TABLE cards
     */

    object MIGRATION_1_2 : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL(
                """
            CREATE TABLE IF NOT EXISTS `cards_new` (
                `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                `accountId` INTEGER NOT NULL,
                `userName` TEXT NOT NULL,
                `cardNumber` TEXT NOT NULL,
                `expiryDate` TEXT NOT NULL,
                `cardCompany` TEXT NOT NULL,
                `color` TEXT NOT NULL,
                `pinCode` TEXT NOT NULL DEFAULT '', 
                `isLocked` INTEGER NOT NULL DEFAULT 0
                 )
        """
            )

            database.execSQL(
                """
            INSERT INTO `cards_new` (id, accountId, userName, cardNumber, expiryDate, cardCompany, color, pinCode, isLocked)
            SELECT id, accountId, userName, cardNumber, expiryDate, cardCompany, color, 
                   COALESCE(pinCode, '') AS pinCode, isLocked 
            FROM `cards`
        """
            )

            database.execSQL("DROP TABLE cards")

            database.execSQL("ALTER TABLE cards_new RENAME TO cards")

            database.execSQL("CREATE INDEX IF NOT EXISTS `index_cards_accountId` ON `cards` (`accountId`)")
        }
    }

    /**
     * changed fields NULL to NOT NULL in TABLE accounts
     */
    object MIGRATION_2_3 : Migration(2, 3) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL(
                """
            CREATE TABLE IF NOT EXISTS `accounts_new` (
                `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                `name` TEXT NOT NULL,
                `surname` TEXT NOT NULL,
                `mobileNumber` TEXT NOT NULL,
                `answer` TEXT NOT NULL,
                `email` TEXT NOT NULL COLLATE NOCASE,
                `photo` TEXT,
                `hash` TEXT NOT NULL,
                `salt` TEXT NOT NULL DEFAULT '',
                `created_at` INTEGER NOT NULL
            )
         """
            )

            database.execSQL(
                """
            INSERT INTO `accounts_new` (id, name, surname, mobileNumber, answer, email, photo, hash, salt, created_at)
            SELECT id, COALESCE(name, '') AS name, COALESCE(surname, '') AS surname,COALESCE(mobileNumber, '') AS mobileNumber, 
            COALESCE(answer, '') AS answer, email, photo, hash, salt, created_at                          
            FROM `accounts`
        """
            )

            database.execSQL("DROP TABLE accounts")

            database.execSQL("ALTER TABLE accounts_new RENAME TO accounts")

            database.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_accounts_email` ON `accounts` (`email`)")
        }
    }

    /**
     * Renamed field "cardCompany" to "cardPaySystem" in "cards" table.
     */
    object MIGRATION_3_4 : Migration(3, 4) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL(
                """
            CREATE TABLE IF NOT EXISTS `cards_new` (
                `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                `accountId` INTEGER NOT NULL,
                `userName` TEXT NOT NULL,
                `cardNumber` TEXT NOT NULL,
                `expiryDate` TEXT NOT NULL,
                `cardPaySystem` TEXT NOT NULL, 
                `color` TEXT NOT NULL,
                `pinCode` TEXT NOT NULL DEFAULT '',
                `isLocked` INTEGER NOT NULL DEFAULT 0
            )
        """
            )

            database.execSQL(
                """
            INSERT INTO `cards_new` (id, accountId, userName, cardNumber, expiryDate, cardPaySystem, color, pinCode, isLocked)
            SELECT id, accountId, userName, cardNumber, expiryDate, cardCompany, color, pinCode, isLocked 
            FROM `cards`
        """
            )

            database.execSQL("DROP TABLE cards")

            database.execSQL("ALTER TABLE cards_new RENAME TO cards")

            database.execSQL("CREATE INDEX IF NOT EXISTS `index_cards_accountId` ON `cards` (`accountId`)")
        }
    }

    abstract fun getAccountsDao(): AccountsDao

    abstract fun getCardsDao(): CardsDao
}