package com.example.test.data.database.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.test.data.enums.AccountType
import com.example.test.data.enums.BudgetType
import kotlinx.parcelize.Parcelize


@Entity
@Parcelize
data class Account(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String,
    val currentBalance: String,
    val dateOfCurrentBalance: String,
    val selectedBudget: BudgetType?,
    val selectedAccountType: AccountType,
    val isClosed: Boolean = false
) : Parcelable