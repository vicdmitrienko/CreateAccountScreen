package com.example.test.data.repository

import com.example.test.data.database.AccountDao
import com.example.test.data.database.mapper.toAccount
import com.example.test.data.database.mapper.toAccountData
import com.example.test.domain.models.Account
import com.example.test.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

//TODO: Мнение: в репозиторий хорошо использовать
// в сценарии «объединение нескольких источников данных».
// В маленьких же проектах можно опускать эту прослойку для экономии времени.
// Но, конечно, не забывать о её возможностях.
class AccountRepositoryImpl(
    private val dao: AccountDao
) : AccountRepository {
    override fun getAllAsFlow(): Flow<List<Account>> {
        return dao.getAllAsFlow().map { accounts -> accounts.map { it.toAccount() } }
    }

    override suspend fun getById(id: Int): Account? {
        return dao.getById(id)?.toAccount()
    }

    override suspend fun insert(account: Account) {
        return dao.insert(account.toAccountData())
    }

    override suspend fun update(account: Account) {
        return dao.update(account.toAccountData())
    }

    override suspend fun delete(account: Account) {
        return dao.delete(account.toAccountData())
    }

}