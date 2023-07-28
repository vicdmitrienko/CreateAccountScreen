package com.example.test.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.test.data.database.entities.Account
import com.example.test.data.enums.AccountAction
import com.example.test.data.enums.AccountsListMode
import com.example.test.ui.account.edit.EditAccountScreen
import com.example.test.ui.account.list.AccountsListScreen
import com.example.test.ui.account.menu.MenuScreen


@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MenuScreen.route) {

        composable(Screen.AccountCreateScreen.route) {
            // Решил использовать Parcelable, так как он быстрее
            // и менее затратный по памяти
            val accountObject: Account? =
                navController.previousBackStackEntry?.savedStateHandle?.get<Account>("account")
            val action = if (accountObject == null) AccountAction.Created else AccountAction.Updated
            // Если сохранили данные счета, то передаем в экран
            EditAccountScreen(
                account = accountObject,
                onCancel = { navController.popBackStack() }, // При отмене возвращаемся на предыдущий экран
                onSuccess = {// При успехе отправляем данные на предыдущий экран и возвращаемся на него
                    navController.previousBackStackEntry?.savedStateHandle?.apply {
                        set("account", it)
                        set("action", action)
                    }
                    navController.popBackStack()
                }
            )
        }

        composable("${Screen.AccountsListScreen.route}/{mode}") { entry ->
            // Получим из сохранённых значений данные об аккаунте
            val account: Account? = entry.savedStateHandle.get<Account>("account")
            val action: AccountAction? = entry.savedStateHandle.get<AccountAction>("action")
            // Режим работы
            val mode = entry.arguments?.getString("mode")
            Log.d("navLog", mode.toString())
            // Сформируем экран с результатами
            mode?.let {
                AccountsListScreen(
                    account = account,
                    action = action,
                    mode = AccountsListMode.valueOf(mode),
                    onAccountClick = { acc ->
                        when (AccountsListMode.valueOf(mode)) {
                            AccountsListMode.Editing -> {
                                // acc == null -> Создание
                                // acc != null -> Редактирование
                                entry.savedStateHandle["account"] = acc
                                // Перейдём на экран создания/редактирования
                                navController.navigate(Screen.AccountCreateScreen.route)
                            }

                            AccountsListMode.Choice -> {
                                // Передаем account Id
                                navController.previousBackStackEntry?.savedStateHandle?.set(
                                    "account_id",
                                    acc?.id
                                )
                                // Возвращаемся на предыдущий экран
                                navController.popBackStack()
                            }
                        }
                    },
                    onCancel = {
                        navController.popBackStack()
                    }
                )
            }
        }

        composable(Screen.MenuScreen.route) { entry ->
            val accountId: Int? = entry.savedStateHandle.get<Int>("account_id")
            MenuScreen(
                accountId = accountId,
                onAccountsClick = {
                    navController.navigate("${Screen.AccountsListScreen.route}/${AccountsListMode.Editing}")
                },
                onChoiceClick = {
                    navController.navigate("${Screen.AccountsListScreen.route}/${AccountsListMode.Choice}")
                }
            )
        }
    }
}
