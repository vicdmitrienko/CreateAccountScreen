package com.example.test.ui.account.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test.data.enums.AccountType
import com.example.test.data.enums.BudgetType
import com.example.test.domain.models.Account
import com.example.test.domain.usecase.AccountUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EditAccountViewModel(
    private val accountUseCases: AccountUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun updateAccountData(accountData: Account) {
        _uiState.update {
            it.copy(
                account = accountData
            )
        }
    }

    fun updateUserName(name: String) {
        _uiState.update {
            it.copy(
                account = it.account.copy(name = name),
                nameError = validateName(name)
            )
        }
    }

    fun updateCurrentBalance(balance: String) {
        _uiState.update {
            it.copy(
                account = it.account.copy(currentBalance = balance),
                currentBalanceError = validateCurrentBalance(balance)
            )
        }
    }

    fun updateCurrentDate(date: String) {
        _uiState.update {
            it.copy(
                account = it.account.copy(dateOfCurrentBalance = date),
                dateOfCurrentBalanceError = validateDate(date)
            )
        }
    }

    fun updateAccountType(type: AccountType) {
        _uiState.update {
            it.copy(
                accountTypeMenuExpanded = false,
                account = it.account.copy(selectedAccountType = type),
                selectedAccountTypeError = validateAccountType(type)
            )
        }
    }

    fun updateBudgetType(budgetType: BudgetType) {
        _uiState.update {
            it.copy(
                account = it.account.copy(selectedBudget = budgetType),
                selectedBudgetError = validateBudgetType(budgetType)
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
        // Валидация всех полей
        _uiState.update {
            it.copy(
                selectedAccountTypeError = validateAccountType(uiState.value.account.selectedAccountType),
                selectedBudgetError = validateBudgetType(uiState.value.account.selectedBudget),
                nameError = validateName(uiState.value.account.name),
                dateOfCurrentBalanceError = validateDate(uiState.value.account.dateOfCurrentBalance),
                currentBalanceError = validateCurrentBalance(uiState.value.account.currentBalance),
            )
        }
        val noErrors: Boolean =
            with(uiState.value) {
                (nameError == null
                        && currentBalanceError == null
                        && dateOfCurrentBalanceError == null
                        && selectedAccountTypeError == null
                        && selectedBudgetError == null)
            }
        // Отправляем наличие ошибок
        viewModelScope.launch {
            accountUseCases.addAccount(account = uiState.value.account)
            // Проверил все ли работает
            onValidated(noErrors)
        }
    }

    //TODO: Уточнить типы данных по всем полям
    data class UiState(
        val account: Account = Account(
            name = "",
            currentBalance = "",
            dateOfCurrentBalance = "",
            selectedBudget = null,
            selectedAccountType = AccountType.NONE
        ),
        val nameError: String? = null,
        val currentBalanceError: String? = null,
        val dateOfCurrentBalanceError: String? = null,
        val selectedBudgetError: String? = null,
        val selectedAccountTypeError: String? = null,
        val accountTypeMenuExpanded: Boolean = false
    )

    // Валидация полей
    private fun validateName(name: String): String? {
        return when {
            name.isEmpty() -> "empty name"
            name.length < 5 -> "name too short"
            else -> null
        }
    }

    private fun validateCurrentBalance(balance: String): String? {
        return when {
            balance.isEmpty() -> "empty balance"
            balance.toDoubleOrNull() == null -> "wrong format"
            else -> null
        }
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