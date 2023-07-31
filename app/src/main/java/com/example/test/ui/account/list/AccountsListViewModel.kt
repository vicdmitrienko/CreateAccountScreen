package com.example.test.ui.account.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test.data.database.daos.AccountDao
import com.example.test.data.database.entities.Account
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AccountsListViewModel(private val dao: AccountDao) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    init {
        getAccounts()
    }

    fun handle(intent: AccountsIntent) {
        when (intent) {
            is AccountsIntent.AddAccount -> addAccount(intent.account)
            is AccountsIntent.DeleteAccount -> deleteAccount(intent.account)
            is AccountsIntent.UpdateAccount -> updateAccount(intent.account)
        }
    }

    private fun addAccount(account: Account) {
        viewModelScope.launch {
            startLoading()
            insertAccDao(account = account)
            endLoading()
        }
    }

    private fun updateAccount(account: Account) {
        viewModelScope.launch {
            startLoading()
            updateAccDao(account = account)
            endLoading()
        }
    }

    private fun deleteAccount(account: Account) {
        viewModelScope.launch {
            startLoading()
            deleteAccDao(account = account)
            endLoading()
        }
    }

    private fun getAccounts() {
        viewModelScope.launch {
            getAccountsAsFlow().collect { accounts ->
                _uiState.update {
                    it.copy(
                        accounts = accounts
                    )
                }
            }
        }
    }

    private fun startLoading() {
        _uiState.update { it.copy(isLoading = true) }
    }

    private fun endLoading() {
        _uiState.update { it.copy(isLoading = false) }
    }

    data class UiState(
        val accounts: List<Account> = emptyList(),
        val isLoading: Boolean = false,
        val error: String? = null
    )

    private fun getAccountsAsFlow() =
        dao.getAllAsFlow()

    private suspend fun insertAccDao(account: Account) =
        dao.insert(accountData = account)

    private suspend fun updateAccDao(account: Account) =
        dao.update(accountData = account)

    private suspend fun deleteAccDao(account: Account) =
        dao.delete(accountData = account)
}