package com.example.test.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.test.data.AccountData
import com.example.test.ui.account.create.CreateAccountScreen
import com.example.test.ui.result.ResultScreen
import com.google.gson.Gson
import com.google.gson.GsonBuilder


@Composable
fun Navigation() {
    val gson: Gson = GsonBuilder().create()
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.ResultScreen.route) {
        composable(
            "${Screen.AccountCreateScreen.route}?accountData={accountData}",
            arguments = listOf(navArgument("accountData") { defaultValue = "" })
        ) { backStackEntry ->
            val accountJson = backStackEntry.arguments?.getString("accountData")
            // Если аргументом передали данные аккаунта, то передаем в экран
            CreateAccountScreen(
                accountData = if (accountJson != null) gson.fromJson(
                    accountJson,
                    AccountData::class.java
                ) else null,
                onCancel = { navController.popBackStack() }, // При отмене возвращаемся на предыдуший экран
                onSuccess = {// При успехе отправляем данные на предыдуший экран и возвращаемся на него
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("user_data", gson.toJson(it))
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.ResultScreen.route)
        { entry ->
            val userJson = entry.savedStateHandle.get<String>("user_data")
            val userObject = gson.fromJson(userJson, AccountData::class.java)
            // Получаем обьект из Json строки
            ResultScreen(navController = navController, accountData = userObject)
        }
    }
}
