package com.example.test.data

import android.os.Parcelable
import com.example.test.data.enums.AccountType
import com.example.test.data.enums.BudgetType
import kotlinx.parcelize.Parcelize

/**
 * Счёт для ведения операций.
 * В дальнейшем:
 * - превратится в @Entity
 * - получит поля: isClosed
 */
@Parcelize
data class AccountData(
    val name: String,
    val currentBalance: String,
    val dateOfCurrentBalance: String,
    val selectedBudget: BudgetType?,
    val selectedAccountType: AccountType,
):Parcelable
