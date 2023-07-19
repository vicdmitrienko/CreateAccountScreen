package com.example.test.data

import com.example.test.data.enums.AccountType
import com.example.test.data.enums.BudgetType

data class AccountCreatingData(
    val name: String,
    val currentBalance: String,
    val dateOfCurrentBalance: String,
    val selectedBudget: BudgetType?,
    val selectedAccountType: AccountType,
)
