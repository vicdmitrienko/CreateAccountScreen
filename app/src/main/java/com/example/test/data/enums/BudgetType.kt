package com.example.test.data.enums

enum class BudgetType(val type: String, val desc: String) {
    BudgetAccount(
        type = "Budget Account",
        desc = "This account should affect my budget"
    ),
    OffBudget(
        type = "Off-Budget",
        desc = "This account should not affect my budget"
    )
}