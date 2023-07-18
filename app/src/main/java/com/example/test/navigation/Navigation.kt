package com.example.test.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.test.UserData
import com.example.test.ui.account.create.CreateAccountScreen
import com.example.test.ui.result.ResultScreen
import com.google.gson.Gson
import com.google.gson.GsonBuilder


@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.AccountCreateScreen.route) {
        composable(Screen.AccountCreateScreen.route) {
            CreateAccountScreen(navController = navController)
        }

        composable(Screen.ResultScreen.route)
        {
            ResultScreen(navController = navController)
        }

        composable("${Screen.ResultScreen.route}/{userData}") {
                navBackStackEntry ->
            val gson: Gson = GsonBuilder().create()
            val userJson = navBackStackEntry.arguments?.getString("userData")
            val userObject = gson.fromJson(userJson, UserData::class.java)
            ResultScreen(navController = navController, userData = userObject)
        }
    }
}