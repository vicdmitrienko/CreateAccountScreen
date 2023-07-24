package com.example.test.data.database.mapper

import com.example.test.data.database.entities.AccountData
import com.example.test.domain.models.Account

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