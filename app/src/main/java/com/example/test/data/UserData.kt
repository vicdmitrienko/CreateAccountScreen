package com.example.test.data

import com.example.test.data.enums.AccountType
import com.example.test.data.enums.BudgetType

//FIXME: Название не говорящее
// И тогда надо отказаться от дублирования этой структуры во ViewModel
data class UserData(
    val name: String,
    val currentBalance: String,
    val dateOfCurrentBalance: String,
    val selectedBudget: BudgetType?,
    val selectedAccountType: AccountType,
)
