package com.example.test.domain.usecase

import com.example.test.domain.repository.AccountRepository

class GetAccounts(private val repository: AccountRepository) {
    operator fun invoke() {
        repository.getAccounts()
    }
}