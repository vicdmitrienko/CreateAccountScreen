package com.example.test.ui.result

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.test.R
import com.example.test.data.AccountData
import com.example.test.navigation.Screen
import com.example.test.ui.theme.PADDING_MED
import com.google.gson.Gson
import com.google.gson.GsonBuilder

//FIXME: Избавляться, так избавляться. Сможете убрать NavController?
@Composable
fun ResultScreen(
    navController: NavController,
    accountData: AccountData? = null
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = PADDING_MED,
            alignment = Alignment.CenterVertically
        ),
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (accountData == null) // Проверяем пришли ли данные
            Text(stringResource(R.string.account_not_created))
        else {
            Text(
                text = "user name = ${accountData.name}\n" +
                        "current balance = ${accountData.currentBalance}\n" +
                        "date = ${accountData.dateOfCurrentBalance}\n" +
                        "account type = ${accountData.selectedAccountType.type}\n" +
                        "budget type = ${accountData.selectedBudget?.type}\n",
                style = MaterialTheme.typography.titleMedium
            )
        }
        Button(onClick = {
            val gson: Gson = GsonBuilder().create()
            // Передаем данные аккаунта, если они есть
            if (accountData != null)
                navController.navigate(
                    "${Screen.AccountCreateScreen.route}?accountData={accountData}"
                        .replace(
                            oldValue = "{accountData}",
                            newValue = gson.toJson(accountData)
                        )
                )
            else
                navController.navigate(Screen.AccountCreateScreen.route)
        }) {
            Text(stringResource(R.string.create_an_account))
        }
    }
}