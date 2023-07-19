package com.example.test.ui.account.create

import androidx.lifecycle.ViewModel
import com.example.test.data.AccountData
import com.example.test.data.enums.AccountType
import com.example.test.data.enums.BudgetType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CreateAccountViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun updateAccountData(accountData: AccountData) {
        _uiState.update {
            it.copy(
                accountData = accountData
            )
        }
    }

    fun updateUserName(name: String) {
        _uiState.update {
            it.copy(
                accountData = it.accountData.copy(name = name),
                nameError = validateName(name)
            )
        }
    }

    fun updateCurrentBalance(balance: String) {
        _uiState.update {
            it.copy(
                accountData = it.accountData.copy(currentBalance = balance),
                currentBalanceError = validateCurrentBalance(balance)
            )
        }
    }

    fun updateCurrentDate(date: String) {
        _uiState.update {
            it.copy(
                accountData = it.accountData.copy(dateOfCurrentBalance = date),
                dateOfCurrentBalanceError = validateDate(date)
            )
        }
    }

    fun updateAccountType(type: AccountType) {
        _uiState.update {
            it.copy(
                accountTypeMenuExpanded = false,
                accountData = it.accountData.copy(selectedAccountType = type),
                selectedAccountTypeError = validateAccountType(type)
            )
        }
    }

    fun updateBudgetType(budgetType: BudgetType) {
        _uiState.update {
            it.copy(
                accountData = it.accountData.copy(selectedBudget = budgetType),
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
        //Валидация всех полей
        _uiState.update {
            it.copy(
                selectedAccountTypeError = validateAccountType(uiState.value.accountData.selectedAccountType),
                selectedBudgetError = validateBudgetType(uiState.value.accountData.selectedBudget),
                nameError = validateName(uiState.value.accountData.name),
                dateOfCurrentBalanceError = validateDate(uiState.value.accountData.dateOfCurrentBalance),
                currentBalanceError = validateCurrentBalance(uiState.value.accountData.currentBalance),
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
        //Отправляем наличие ошибок
        onValidated(noErrors)
    }


    //TODO: Уточнить типы данных по всем полям
    data class UiState(
        val accountData: AccountData = AccountData(
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

    //Валидация полей
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