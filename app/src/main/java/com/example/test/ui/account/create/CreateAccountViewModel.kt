package com.example.test.ui.account.create

import androidx.lifecycle.ViewModel
import com.example.test.AccountType
import com.example.test.BudgetType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CreateAccountViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun updateUserName(name: String) {
        _uiState.update {
            it.copy(
                name = name, nameError = validateName(name)
            )
        }
    }

    fun updateCurrentBalance(balance: String) {
        _uiState.update {
            it.copy(
                currentBalance = balance, currentBalanceError = validateCurrentBalance(balance)
            )
        }
    }

    fun updateCurrentDate(date: String) {
        _uiState.update {
            it.copy(
                dateOfCurrentBalance = date, dateOfCurrentBalanceError = validateDate(date)
            )
        }
    }

    fun updateAccountType(type: AccountType) {
        _uiState.update {
            it.copy(
                accountTypeMenuExpanded = false,
                selectedAccountType = type,
                selectedAccountTypeError = validateAccountType(type)
            )
        }
    }

    fun updateBudgetType(budgetType: BudgetType) {
        _uiState.update {
            it.copy(
                selectedBudget = budgetType, selectedBudgetError = validateBudgetType(budgetType)
            )
        }
    }

    fun updateAccountTypeExpanded(isExpanded: Boolean) {
        _uiState.update {
            it.copy(
                accountTypeMenuExpanded = isExpanded
            )
        }
    }

    fun createAccount(onValidated: (Boolean) -> Unit) {
        _uiState.update {
            it.copy(
                selectedAccountTypeError = validateAccountType(uiState.value.selectedAccountType),
                selectedBudgetError = validateBudgetType(uiState.value.selectedBudget),
                nameError = validateName(uiState.value.name),
                dateOfCurrentBalanceError = validateDate(uiState.value.dateOfCurrentBalance),
                currentBalanceError = validateCurrentBalance(uiState.value.currentBalance),
            )
        }
        var hasErrors: Boolean
        with(uiState.value) {
            hasErrors =
                (nameError != null
                        || currentBalanceError != null
                        || dateOfCurrentBalanceError != null
                        || selectedAccountTypeError != null
                        || selectedBudgetError != null)
        }
        onValidated(!hasErrors)
    }

    //TODO: Уточнить типы данных по всем полям
    data class UiState(
        val name: String = "",
        val nameError: String? = null,
        val currentBalance: String = "",
        val currentBalanceError: String? = null,
        val dateOfCurrentBalance: String = "",
        val dateOfCurrentBalanceError: String? = null,
        val selectedBudget: BudgetType? = null,
        val selectedBudgetError: String? = null,
        val selectedAccountType: AccountType = AccountType.NONE,
        val selectedAccountTypeError: String? = null,
        val accountTypeMenuExpanded: Boolean = false
    )

    private fun validateName(name: String): String? {
        return when {
            name.isEmpty() -> "empty name"
            name.length < 5 -> "name too short"
            else -> null
        }
    }

    private fun validateCurrentBalance(balance: String): String? {
        return if (balance.isEmpty()) "empty balance" else null
    }

    private fun validateDate(date: String): String? {
        return if (date.isEmpty()) "empty date" else null
    }

    private fun validateAccountType(type: AccountType): String? {
        return if (type == AccountType.NONE) "account not selected" else null
    }

    private fun validateBudgetType(type: BudgetType?): String? {
        return if (type == null) "select budget affect" else null
    }

}