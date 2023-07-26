package com.example.test.ui.account.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.test.R
import com.example.test.data.database.entities.Account
import com.example.test.data.enums.AccountAction
import com.example.test.data.enums.AccountsListMode
import com.example.test.ui.common.components.CommonAppBar
import com.example.test.ui.theme.BUTTON_HEIGHT_BIG
import com.example.test.ui.theme.PADDING_BIG
import com.example.test.ui.theme.PADDING_MED
import com.example.test.ui.theme.PADDING_SMALL
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountsListScreen(
    thisViewModel: AccountsListViewModel = koinViewModel(),
    onAccountClick: (Account?) -> Unit,
    mode: AccountsListMode?,
    account: Account? = null,
    action: AccountAction? = null,
    onCancel: () -> Unit
) {
    // Прогресс бар при переходе между экранами
    if (mode == null) {
        Box(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    } else {

        val uiState by thisViewModel.uiState.collectAsState()
        val title = if (mode == AccountsListMode.Editing) stringResource(R.string.editing)
        else stringResource(R.string.choice)

        account?.let {
            LaunchedEffect(it) {
                when (action) {
                    AccountAction.Created -> thisViewModel.addAccount(it)
                    AccountAction.Updated -> thisViewModel.editAccount(it)
                    else -> {}
                }
            }
        }

        Scaffold(
            topBar = {
                CommonAppBar(title = title, onBackClick = onCancel)
            },
            floatingActionButton = {
                if (mode == AccountsListMode.Editing)
                    FloatingActionButton(onClick = { onAccountClick(null) }) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "addIcon"
                        )
                    }
            },
            floatingActionButtonPosition = FabPosition.End
        ) { padding ->
            AccountsListBody(
                padding = padding,
                mode = mode,
                uiState = uiState,
                thisViewModel = thisViewModel,
                onAccountClick = onAccountClick
            )
        }
    }
}


@Composable
fun AccountsListBody(
    padding: PaddingValues,
    mode: AccountsListMode,
    thisViewModel: AccountsListViewModel,
    uiState: AccountsListViewModel.UiState,
    onAccountClick: (Account?) -> Unit
) {
    LazyColumn(
        Modifier
            .padding(padding)
            .padding(vertical = PADDING_MED, horizontal = PADDING_BIG)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(PADDING_SMALL)
    ) {
        when (mode) {
            AccountsListMode.Editing ->
                items(uiState.accounts.size) {
                    AccountItemEditable(
                        account = uiState.accounts[it],
                        onClick = { onAccountClick(uiState.accounts[it]) },
                        onDelete = { thisViewModel.deleteAccount(uiState.accounts[it]) }
                    )
                }

            AccountsListMode.Choice -> {
                items(uiState.accounts.size) {
                    AccountItem(
                        account = uiState.accounts[it],
                        onClick = { onAccountClick(uiState.accounts[it]) }
                    )
                }
            }
        }
    }
    if (uiState.accounts.isEmpty())
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
fun AccountItemEditable(
    account: Account,
    onClick: () -> Unit,
    onDelete: () -> Unit,
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(BUTTON_HEIGHT_BIG),
        onClick = onClick,
    ) {
        Text(
            text = account.name,
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = onDelete) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "delete_account"
            )
        }
    }
}

@Composable
fun AccountItem(
    account: Account,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(BUTTON_HEIGHT_BIG),
        onClick = onClick,
    ) {
        Text(
            text = account.name,
            style = MaterialTheme.typography.titleLarge
        )
    }
}