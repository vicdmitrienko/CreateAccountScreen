package com.example.test.ui.account.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.test.R
import com.example.test.data.database.entities.Account
import com.example.test.data.enums.AccountAction
import com.example.test.data.enums.AccountsListMode
import com.example.test.ui.common.components.CommonAppBar
import com.example.test.ui.theme.AppTheme
import com.example.test.ui.theme.BUTTON_HEIGHT_BIG
import com.example.test.ui.theme.PADDING_BIG
import com.example.test.ui.theme.PADDING_MED
import com.example.test.ui.theme.PADDING_SMALL


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountsListScreen(
    uiState: AccountsListViewModel.UiState,
    onAccountClick: (Account?) -> Unit,
    mode: AccountsListMode,
    account: Account? = null,
    action: AccountAction? = null,
    onCancel: () -> Unit,
    onIntent: (AccountsIntent) -> Unit
) {
    val title = if (mode == AccountsListMode.Editing) stringResource(R.string.editing)
    else stringResource(R.string.choice)

    account?.let {
        LaunchedEffect(it) {
            when (action) {
                AccountAction.Created -> onIntent(AccountsIntent.AddAccount(it))
                AccountAction.Updated -> onIntent(AccountsIntent.UpdateAccount(it))
                AccountAction.Deleted -> onIntent(AccountsIntent.DeleteAccount(it))

                null -> {}
            }
        }
    }

    Scaffold(topBar = {
        CommonAppBar(title = title, onBackClick = onCancel)
    }, floatingActionButton = {
        if (mode == AccountsListMode.Editing) FloatingActionButton(onClick = {
            onAccountClick(
                null
            )
        }) {
            Icon(
                Icons.Default.Add, contentDescription = "addIcon"
            )
        }
    }, floatingActionButtonPosition = FabPosition.End
    ) { padding ->
        when {
            uiState.isLoading -> LoadingScreen()
            uiState.accounts.isEmpty() ->
                when (mode) {
                    AccountsListMode.Editing -> EmptyListScreenEditable()

                    AccountsListMode.Choice -> EmptyListScreen()
                }

            else -> AccountsListBody(
                padding = padding,
                uiState = uiState,
                onAccountClick = onAccountClick
            )
        }
    }
}

@Composable
fun AccountsListBody(
    padding: PaddingValues,
    uiState: AccountsListViewModel.UiState,
    onAccountClick: (Account?) -> Unit
) {
    LazyColumn(
        Modifier
            .padding(padding)
            .padding(vertical = PADDING_MED, horizontal = PADDING_BIG)
            .fillMaxSize(), verticalArrangement = Arrangement.spacedBy(PADDING_SMALL)
    ) {
        items(uiState.accounts.size) {
            AccountItem(account = uiState.accounts[it],
                onClick = { onAccountClick(uiState.accounts[it]) })
        }
    }
}


@Composable
fun AccountItem(
    account: Account, onClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(BUTTON_HEIGHT_BIG),
        onClick = onClick,
    ) {
        Text(
            text = account.name, style = MaterialTheme.typography.titleLarge
        )
    }
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
    ) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun EmptyListScreen() {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
    ) {
        Text(
            text = stringResource(R.string.no_accounts),
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun EmptyListScreenEditable() {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
    ) {
        Text(
            text = "No accounts\nPress + button to create",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}


@Preview
@Composable
fun EditingList() {
    AppTheme {
        AccountsListScreen(
            mode = AccountsListMode.Editing,
            uiState = AccountsListViewModel.UiState(),
            onAccountClick = {},
            onCancel = {},
            onIntent = {},
        )
    }

}

@Preview
@Composable
fun ChoiceList() {
    AppTheme {
        AccountsListScreen(
            mode = AccountsListMode.Choice,
            uiState = AccountsListViewModel.UiState(),
            onAccountClick = {},
            onIntent = {},
            onCancel = {},
        )
    }
}