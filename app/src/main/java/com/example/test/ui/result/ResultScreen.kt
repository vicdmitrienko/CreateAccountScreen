package com.example.test.ui.result

import androidx.compose.foundation.background
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
import com.example.test.domain.models.Account
import com.example.test.ui.theme.PADDING_MED

@Composable
fun ResultScreen(
    account: Account? = null,
    onCreateOrEditAccount: (accountData: Account?) -> Unit
) {
    val buttonText: String
    val bodyText: String

    if (account == null) {
        buttonText = stringResource(id = R.string.create_account)
        bodyText = stringResource(R.string.account_not_created)
    } else {
        buttonText = stringResource(id = R.string.update_account)
        bodyText = "user name = ${account.name}\n" +
                "current balance = ${account.currentBalance}\n" +
                "date = ${account.dateOfCurrentBalance}\n" +
                "account type = ${account.selectedAccountType.type}\n" +
                "budget type = ${account.selectedBudget?.type}\n"
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = PADDING_MED, alignment = Alignment.CenterVertically
        ),
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)
    ) {
        Text(bodyText, color = MaterialTheme.colorScheme.onBackground)
        Button(onClick = {
            // Передаем данные аккаунта
            onCreateOrEditAccount(account)
        }) {
            Text(buttonText)
        }
    }
}