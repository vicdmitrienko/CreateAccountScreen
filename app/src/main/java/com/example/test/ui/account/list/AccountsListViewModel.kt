package com.example.test.ui.account.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test.data.database.daos.AccountDao
import com.example.test.data.database.entities.Account
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AccountsListViewModel(private val dao: AccountDao) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()
    
    private var getAccountsJob: Job? = null

    init {
        getAccounts()
    }

    fun addAccount(account: Account) {
        viewModelScope.launch {
            insertAcc(account = account)
        }
    }

    fun editAccount(account: Account){
        viewModelScope.launch {
            updateAcc(account = account)
        }
    }

    fun deleteAccount(account: Account){
        viewModelScope.launch {
            deleteAcc(account = account)
        }
    }

    private fun getAccounts() {
        getAccountsJob?.cancel()

        //TODO: Почему не collect?
        getAccountsJob = getAccountsAsFlow().onEach { accounts ->
            _uiState.update {
                it.copy(
                    accounts = accounts
                )
            }
            
        }.launchIn(viewModelScope)
    }


    data class UiState(
        val accounts: List<Account> = emptyList()
    )


    private fun getAccountsAsFlow() =
        dao.getAllAsFlow()

    private suspend fun insertAcc(account: Account) =
        dao.insert(accountData = account)

    private suspend fun updateAcc(account: Account) =
        dao.update(accountData = account)

    private suspend fun deleteAcc(account: Account) =
        dao.delete(accountData = account)
}