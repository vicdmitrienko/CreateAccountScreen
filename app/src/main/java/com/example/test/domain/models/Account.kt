package com.example.test.domain.models

import android.os.Parcelable
import com.example.test.data.enums.AccountType
import com.example.test.data.enums.BudgetType
import kotlinx.parcelize.Parcelize

@Parcelize
data class Account(
    val id:Int? = null,
    val name: String,
    val currentBalance: String,
    val dateOfCurrentBalance: String,
    val selectedBudget: BudgetType?,
    val selectedAccountType: AccountType,
    val isClosed: Boolean = false
) : Parcelable
