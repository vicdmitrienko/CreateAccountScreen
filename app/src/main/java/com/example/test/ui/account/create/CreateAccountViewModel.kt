package com.example.test.ui.account.create

import androidx.lifecycle.ViewModel
import com.example.test.BudgetType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CreateAccountViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun updateUserName(name:String) {
        _uiState.update {
            it.copy(
                name = name
            )
        }
    }

    fun updateCurrentBalance(balance:String) {
        _uiState.update {
            it.copy(
                currentBalance = balance
            )
        }
    }

    fun updateCurrentDate(date:String) {
        _uiState.update {
            it.copy(
                dateOfCurrentBalance = date
            )
        }
    }

    fun updateAccountType(type:String) {
        _uiState.update {
            it.copy(
                accountTypeMenuExpanded = false,
                selectedAccountType = type
            )
        }
    }

    fun updateBudgetType(budgetType: BudgetType) {
        _uiState.update {
            it.copy(
                selectedBudget = budgetType
            )
        }
    }

    fun updateAccountTypeExpanded(isExpanded:Boolean){
        _uiState.update {
            it.copy(
                accountTypeMenuExpanded = isExpanded
            )
        }
    }

    //TODO: Уточнить типы данных по всем полям
    data class UiState(
        val name: String = "",
        val currentBalance: String = "",
        val dateOfCurrentBalance: String = "",
        val selectedBudget: BudgetType? = null,
        val selectedAccountType: String = "Select an Account Type",
        val accountTypeMenuExpanded: Boolean = false
    )
}