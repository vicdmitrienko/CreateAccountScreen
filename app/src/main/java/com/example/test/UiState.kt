package com.example.test

data class UiState(
    val name: String = "",
    val currentBalance: String = "",
    val dateOfCurrentBalance: String = "",
    val selectedBudget: BudgetType? = null,
    val selectedAccountType: String = "Select an Account Type",
    val accountTypeMenuExpanded: Boolean = false
)
