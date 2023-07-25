package com.example.test.domain.usecase

import com.example.test.domain.models.Account
import com.example.test.domain.repository.AccountRepository

class AddAccount(private val repository: AccountRepository) {
    suspend operator fun invoke(account: Account){
        repository.insert(account = account)
    }
}