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
import com.example.test.R
import com.example.test.data.AccountData
import com.example.test.ui.theme.PADDING_MED

@Composable
fun ResultScreen(
    accountData: AccountData? = null,
    onCreateOrEditAccount: (accountData: AccountData?) -> Unit
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
            // Передаем данные аккаунта
            onCreateOrEditAccount(accountData)
        }) {
            Text(stringResource(R.string.create_an_account))
        }
    }
}