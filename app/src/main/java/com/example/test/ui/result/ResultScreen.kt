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
import com.example.test.UserData
import com.example.test.ui.theme.PADDING_MED

@Composable
fun ResultScreen(
    navController: NavController,
    userData: UserData? = null
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
        if (userData == null)
            Text(stringResource(R.string.account_not_created))
        else {
            Text(
                text = "user name = ${userData.name}\n" +
                        "current balance = ${userData.currentBalance}\n" +
                        "date = ${userData.dateOfCurrentBalance}\n" +
                        "account type = ${userData.selectedAccountType.type}\n" +
                        "budget type = ${userData.selectedBudget?.type}\n",
                style = MaterialTheme.typography.titleMedium
            )
        }
        Button(onClick = { navController.navigate("account_create_screen") }) {
            Text(stringResource(R.string.create_an_account))
        }
    }
}