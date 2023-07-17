package com.example.test

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun updateUserName(name:String){
        _uiState.update {
            it.copy(
                name = name
            )
        }
    }

    fun updateCurrentBalance(balance:String){
        _uiState.update {
            it.copy(
                currentBalance = balance
            )
        }
    }

    fun updateCurrentDate(date:String){
        _uiState.update {
            it.copy(
                dateOfCurrentBalance = date
            )
        }
    }

    fun updateAccountType(type:String){
        _uiState.update {
            it.copy(
                accountTypeMenuExpanded = false,
                selectedAccountType = type
            )
        }
    }

    fun updateBudgetType(budgetType:BudgetType){
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
}