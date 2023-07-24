package com.example.test.domain.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.test.data.enums.AccountType
import com.example.test.data.enums.BudgetType
import kotlinx.parcelize.Parcelize

//TODO: Я бы предложил этот класс разметить тут: com.example.test.data.database.entities.
// но интересно знать ваше мнение и аргументацию. Вдруг я вас «веду по ошибочному пути»?

@Parcelize
@Entity
data class AccountData(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String,
    val currentBalance: String,
    val dateOfCurrentBalance: String,
    val selectedBudget: BudgetType?,
    val selectedAccountType: AccountType,
    val isClosed: Boolean = false
): Parcelable
