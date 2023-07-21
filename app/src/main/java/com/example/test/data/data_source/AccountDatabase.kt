package com.example.test.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.test.domain.models.AccountData

@Database(
    entities = [AccountData::class],
    version = 1
)
abstract class AccountDatabase: RoomDatabase() {
    abstract val accountDao: AccountDao

    companion object {
        const val DATABASE_NAME = "accounts_db"
    }
}