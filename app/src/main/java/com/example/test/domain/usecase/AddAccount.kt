package com.example.test.domain.usecase

import com.example.test.domain.models.AccountData
import com.example.test.domain.repository.AccountRepository

class AddAccount(private val repository: AccountRepository) {
    suspend operator fun invoke(account: AccountData){
        repository.insertAccount(account = account)
    }
}