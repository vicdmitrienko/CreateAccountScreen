package com.example.test.data.database.mapper

import com.example.test.data.database.entities.AccountData
import com.example.test.domain.models.Account

// Понимаю ваше стремление к идеалам.
// Но именно сейчас эти объекты идентичны в слоях данных и логики.
// Если мы хотим быстро достичь результата, но правила идеального мира можно упрощать.
// Помнить о правилах, но не боятся их осознанно опускать.

fun AccountData.toAccount() =
    Account(
        id = this.id,
        name = this.name,
        currentBalance = this.currentBalance,
        dateOfCurrentBalance = this.dateOfCurrentBalance,
        selectedBudget = this.selectedBudget,
        selectedAccountType = this.selectedAccountType,
        isClosed = this.isClosed
    )

fun Account.toAccountData() =
    AccountData(
        id = this.id,
        name = this.name,
        currentBalance = this.currentBalance,
        dateOfCurrentBalance = this.dateOfCurrentBalance,
        selectedBudget = this.selectedBudget,
        selectedAccountType = this.selectedAccountType,
        isClosed = this.isClosed
    )