package com.example.test.data.data_source

import androidx.room.*
import com.example.test.domain.models.AccountData
import kotlinx.coroutines.flow.Flow


@Dao
interface AccountDao {
    @Query("SELECT * FROM accountData")
    fun getAccounts(): Flow<List<AccountData>>

    @Query("SELECT * FROM accountData WHERE id = :id")
    suspend fun getAccountById(id: Int): AccountData?


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAccount(accountData: AccountData)

    @Update
    suspend fun updateAccount(accountData: AccountData)

    @Delete
    suspend fun deleteAccount(accountData: AccountData)
}