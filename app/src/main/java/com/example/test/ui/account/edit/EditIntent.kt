package com.example.test.ui.account.edit

import com.example.test.data.database.entities.Account
import com.example.test.data.enums.AccountType
import com.example.test.data.enums.BudgetType

sealed class EditIntent {
    object OpenDeleteDialog : EditIntent()
    object CloseDeleteDialog : EditIntent()
    class UpdateAccountData(val account: Account) : EditIntent()
    class UpdateUserName(val name: String) : EditIntent()
    class UpdateCurrentBalance(val balance: String) : EditIntent()
    class UpdateCurrentDate(val date: String) : EditIntent()
    class UpdateAccountType(val type: AccountType) : EditIntent()
    class UpdateBudgetType(val budget: BudgetType) : EditIntent()
    class UpdateAccountTypeExpanded(val isExpanded: Boolean) : EditIntent()
    class CreateAccount(val onValidated: (Boolean) -> Unit) : EditIntent()
}