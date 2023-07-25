package com.example.test.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.test.data.database.entities.AccountData

@Database(
    entities = [AccountData::class],
    version = 1
)
abstract class AccountDatabase : RoomDatabase() {
    abstract val accountDao: AccountDao

    companion object {
        const val DATABASE_NAME = "accounts_db"
    }
}