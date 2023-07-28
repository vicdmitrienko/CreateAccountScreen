package com.example.test.ui.account.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.test.R
import com.example.test.ui.theme.AppTheme
import com.example.test.ui.theme.PADDING_BIG
import com.example.test.ui.theme.SPACE_MED

@Composable
fun MenuScreen(
    uiState: MenuViewModel.UiState,
    onIntent: (MenuIntent) -> Unit,
    onChoiceClick: () -> Unit,
    onAccountsClick: () -> Unit,
    accountId: Int? = null,
) {
    accountId?.let {
        LaunchedEffect(it) {
            onIntent(MenuIntent.UpdateActiveAccount(it))
        }
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .padding(PADDING_BIG),
        verticalArrangement = Arrangement.spacedBy(
            space = SPACE_MED,
            alignment = Alignment.CenterVertically
        ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.menu),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.displaySmall
        )
        Button(
            onClick = { onAccountsClick() },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.accounts))
        }
        Button(
            onClick = { onChoiceClick() },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.choice_account))
        }
        Text(
            text = if (uiState.accountName == null) stringResource(R.string.account_not_active)
            else stringResource(R.string.account_active) + uiState.accountName,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
@Preview
fun MenuPrev() {
    AppTheme() {
        MenuScreen(
            onChoiceClick = { /*TODO*/ },
            onAccountsClick = { /*TODO*/ },
            uiState = MenuViewModel.UiState(),
            onIntent = {}
        )
    }
}