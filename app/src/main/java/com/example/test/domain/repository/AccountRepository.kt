package com.example.test.domain.repository

import com.example.test.domain.models.Account
import kotlinx.coroutines.flow.Flow

interface AccountRepository {
    fun getAllAsFlow(): Flow<List<Account>>

    suspend fun getById(id: Int): Account?

    suspend fun insert(account: Account)

    suspend fun update(account: Account)

    suspend fun delete(account: Account)
}