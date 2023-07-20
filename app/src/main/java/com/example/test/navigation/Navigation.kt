package com.example.test.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.test.data.AccountData
import com.example.test.ui.account.edit.EditAccountScreen
import com.example.test.ui.result.ResultScreen


@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.ResultScreen.route) {
        composable(
            Screen.AccountCreateScreen.route,
        ) {
            // Решил использовать Parcelable, так как он быстрее
            // и менее затратный по памяти
            val userObject = navController.previousBackStackEntry?.savedStateHandle?.get<AccountData>("account")
            // Если сохранили данные счета, то передаем в экран
            EditAccountScreen(
                accountData = userObject,
                onCancel = { navController.popBackStack() }, // При отмене возвращаемся на предыдущий экран
                onSuccess = {// При успехе отправляем данные на предыдущий экран и возвращаемся на него
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("account", it)
                    navController.popBackStack()
                },
            )
        }

        composable(Screen.ResultScreen.route) { entry ->
            val userObject = entry.savedStateHandle.get<AccountData>("account")
            // Получаем данные счета и передаем в экран
            ResultScreen(
                accountData = userObject,
                navigateToEditAccountScreen = { account ->
                    if (account != null) {
                        entry.savedStateHandle["account"] = account
                        navController.navigate(Screen.AccountCreateScreen.route)
                    } else navController.navigate(Screen.AccountCreateScreen.route)
                }
            )
        }
    }
}
