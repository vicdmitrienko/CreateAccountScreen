package com.example.test.domain.repository

import com.example.test.domain.models.AccountData
import kotlinx.coroutines.flow.Flow

interface AccountRepository {
    fun getAccounts(): Flow<List<AccountData>>

    suspend fun getAccountById(id: Int): AccountData?

    suspend fun insertAccount(account: AccountData)

    suspend fun deleteAccount(account: AccountData)
}