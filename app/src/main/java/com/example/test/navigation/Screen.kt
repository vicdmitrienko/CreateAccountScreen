package com.example.test.navigation

sealed class Screen(val route: String) {
    object ResultScreen: Screen("result_screen")
    object AccountCreateScreen: Screen("account_create_screen")
    object AccountsListScreen: Screen("accounts_list_screen")
    object MenuScreen: Screen("menu_screen")
}