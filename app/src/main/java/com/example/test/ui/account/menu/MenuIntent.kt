package com.example.test.ui.account.menu

sealed class MenuIntent {
    class UpdateActiveAccount(val accountId: Int) : MenuIntent()
}