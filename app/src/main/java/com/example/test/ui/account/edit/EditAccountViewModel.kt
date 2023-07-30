package com.example.test.ui.account.edit

import androidx.lifecycle.ViewModel
import com.example.test.data.database.entities.Account
import com.example.test.data.enums.AccountType
import com.example.test.data.enums.BudgetType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class EditAccountViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun handle(intent: EditIntent) {
        when (intent) {
            is EditIntent.OpenDeleteDialog -> openDeleteDialog()
            is EditIntent.CloseDeleteDialog -> closeDeleteDialog()
            is EditIntent.UpdateAccountData -> updateAccountData(intent.account)
            is EditIntent.UpdateUserName -> updateUserName(intent.name)
            is EditIntent.UpdateCurrentBalance -> updateCurrentBalance(intent.balance)
            is EditIntent.UpdateCurrentDate -> updateCurrentDate(intent.date)
            is EditIntent.UpdateAccountType -> updateAccountType(intent.type)
            is EditIntent.UpdateBudgetType -> updateBudgetType(intent.budget)
            is EditIntent.UpdateAccountTypeExpanded -> updateAccountTypeExpanded(intent.isExpanded)
            is EditIntent.CreateAccount -> createAccount(intent.onValidated)
        }
    }

    private fun openDeleteDialog() {
        _uiState.update {
            it.copy(
                deleteDialogOpened = true
            )
        }
    }

    private fun closeDeleteDialog() {
        _uiState.update {
            it.copy(
                deleteDialogOpened = false
            )
        }
    }

    private fun updateAccountData(accountData: Account) {
        _uiState.update {
            it.copy(
                account = accountData
            )
        }
    }

    private fun updateUserName(name: String) {
        _uiState.update {
            it.copy(
                account = it.account.copy(name = name),
                nameError = validateName(name)
            )
        }
    }

    private fun updateCurrentBalance(balance: String) {
        _uiState.update {
            it.copy(
                account = it.account.copy(currentBalance = balance),
                currentBalanceError = validateCurrentBalance(balance)
            )
        }
    }

    private fun updateCurrentDate(date: String) {
        _uiState.update {
            it.copy(
                account = it.account.copy(dateOfCurrentBalance = date),
                dateOfCurrentBalanceError = validateDate(date)
            )
        }
    }

    private fun updateAccountType(type: AccountType) {
        _uiState.update {
            it.copy(
                accountTypeMenuExpanded = false,
                account = it.account.copy(selectedAccountType = type),
                selectedAccountTypeError = validateAccountType(type)
            )
        }
    }

    private fun updateBudgetType(budgetType: BudgetType) {
        _uiState.update {
            it.copy(
                account = it.account.copy(selectedBudget = budgetType),
                selectedBudgetError = validateBudgetType(budgetType)
            )
        }
    }

    private fun updateAccountTypeExpanded(isExpanded: Boolean) {
        _uiState.update {
            it.copy(
                accountTypeMenuExpanded = isExpanded
            )
        }
    }

    private fun createAccount(onValidated: (Boolean) -> Unit) {
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
        onValidated(noErrors)
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
        val deleteDialogOpened: Boolean = false,
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