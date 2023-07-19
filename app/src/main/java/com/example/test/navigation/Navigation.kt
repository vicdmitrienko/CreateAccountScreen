package com.example.test.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.test.data.AccountCreatingData
import com.example.test.ui.account.create.CreateAccountScreen
import com.example.test.ui.result.ResultScreen
import com.google.gson.Gson
import com.google.gson.GsonBuilder


@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.ResultScreen.route) {
        composable(Screen.AccountCreateScreen.route) {
            CreateAccountScreen(
                onCancel = { navController.popBackStack() }, // При отмене возвращаемся на предыдуший экран
                onSuccess = {// При успехе отправляем данные на предыдуший экран и возвращаемся на него
                    val gson: Gson = GsonBuilder().create()
                    val userJson = gson.toJson(it)// Конвертируем обьект в Json
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("user_data", userJson)
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.ResultScreen.route)
        { entry ->
            val gson: Gson = GsonBuilder().create()
            val userJson = entry.savedStateHandle.get<String>("user_data")
            val userObject = gson.fromJson(userJson, AccountCreatingData::class.java)
            // Получаем обьект из Json строки
            ResultScreen(navController = navController, accountCreatingData = userObject)
        }
    }
}
