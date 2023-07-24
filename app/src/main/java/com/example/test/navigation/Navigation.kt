package com.example.test.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.test.domain.models.Account
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
            val accountObject: Account? =
                navController.previousBackStackEntry?.savedStateHandle?.get<Account>("account")
            // Если сохранили данные счета, то передаем в экран
            EditAccountScreen(
                account = accountObject,
                onCancel = { navController.popBackStack() }, // При отмене возвращаемся на предыдущий экран
                onSuccess = {// При успехе отправляем данные на предыдущий экран и возвращаемся на него
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("account", it)
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.ResultScreen.route) { entry ->
            // Получим из сохранённых значений данные об аккаунте
            val account: Account? =
                // в некоторых случая удобнее потом разбираться и отлаживать, если указать тип.
                entry.savedStateHandle.get<Account>("account")
            // Сформируем экран с результатами
            ResultScreen(
                account = account,
                onCreateOrEditAccount = {
                    it?.let {
                        // Положим результат в сохранённые значения
                        entry.savedStateHandle["account"] = account
                    }
                    // Перейдём на экран создания/редактирования
                    navController.navigate(Screen.AccountCreateScreen.route)
                }
            )
        }
    }
}
