package com.example.test.data.repository

import com.example.test.data.data_source.AccountDao
import com.example.test.domain.models.AccountData
import com.example.test.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow

//TODO: Мнение: в репозиторий хорошо использовать
// в сценарии «объединение нескольких источников данных».
// В маленьких же проектах можно опускать эту прослойку для экономии времени.
// Но, конечно, не забывать о её возможностях.
//TODO: Именование причесать, как в Dao
class AccountRepositoryImpl(
    private val dao: AccountDao
): AccountRepository {
    override fun getAccounts(): Flow<List<AccountData>> {
        return dao.getAccounts()
    }

    override suspend fun getAccountById(id: Int): AccountData? {
        return dao.getAccountById(id)
    }

    override suspend fun insertAccount(account: AccountData) {
        return dao.insertAccount(account)
    }

    override suspend fun deleteAccount(account: AccountData) {
       return dao.deleteAccount(account)
    }

}