package com.example.test.ui.account.list

import com.example.test.data.database.entities.Account

sealed class AccountsIntent {
    class AddAccount(val account: Account) : AccountsIntent()
    class UpdateAccount(val account: Account) : AccountsIntent()
    class DeleteAccount(val account: Account) : AccountsIntent()
}