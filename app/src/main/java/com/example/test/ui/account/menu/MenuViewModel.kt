package com.example.test.ui.account.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test.data.database.daos.AccountDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MenuViewModel(
    private val dao: AccountDao
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun updateActiveAccount(accountId: Int) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                   accountName = getAccountById(accountId)?.name
                )
            }
        }
    }


    data class UiState(
        val accountName: String? = null
    )


    private suspend fun getAccountById(id: Int) =
        dao.getById(id)
}