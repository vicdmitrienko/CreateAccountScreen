package com.example.test.ui.account.edit

import android.app.DatePickerDialog
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.test.R
import com.example.test.data.AccountData
import com.example.test.data.enums.AccountType
import com.example.test.data.enums.BudgetType
import com.example.test.ui.common.components.CommonAppBar
import com.example.test.ui.theme.AppTheme
import com.example.test.ui.theme.CHECKBOX_SIZE_SMALL
import com.example.test.ui.theme.PADDING_BIG
import com.example.test.ui.theme.PADDING_MED
import com.example.test.ui.theme.PADDING_SMALL
import java.util.Calendar
import java.util.Date

private const val TAG = "EditAccountScreen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditAccountScreen(
    accountData: AccountData? = null,
    onSuccess: (AccountData) -> Unit,
    onCancel: () -> Unit
) {
    val thisViewModel: EditAccountViewModel = viewModel()

    //TODO: Разберитесь с LaunchedEffect. Что такое ключ (параметры)
    if (accountData != null) {
        LaunchedEffect(Unit) {
            thisViewModel.updateAccountData(accountData)
            Log.e(TAG, "thisViewModel.updateAccountData(accountData)")
        }
    }

    val uiState by thisViewModel.uiState.collectAsState()

    Scaffold(topBar = {
        CommonAppBar(stringResource(R.string.create_account), onBackClick = onCancel)
    }) { padding ->
        CreateAccountBody(
            padding = padding,
            uiState = uiState,
            viewModel = thisViewModel,
            onSuccess = onSuccess
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CreateAccountBody(
    padding: PaddingValues,
    uiState: EditAccountViewModel.UiState,
    viewModel: EditAccountViewModel,
    onSuccess: (accountData: AccountData) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(padding)
            .padding(vertical = PADDING_MED, horizontal = PADDING_BIG)
            .verticalScroll(state = rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(PADDING_BIG)
    ) {

        OutlinedTextField(
            value = uiState.accountData.name,
            onValueChange = { viewModel.updateUserName(it) },
            label = {
                Text(stringResource(R.string.name))
            },
            // Вывод сообщения об ошибке
            supportingText = {
                if (uiState.nameError != null)
                    Text(uiState.nameError)
            },
            isError = uiState.nameError != null,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            }),
            modifier = Modifier
                .fillMaxWidth()
        )

        OutlinedTextField(
            value = uiState.accountData.currentBalance,
            onValueChange = { viewModel.updateCurrentBalance(it) },
            supportingText = {
                if (uiState.currentBalanceError != null)
                    Text(uiState.currentBalanceError)
                else
                    Text(stringResource(R.string.balance_hint))
            },
            isError = uiState.currentBalanceError != null,
            label = {
                Text(stringResource(R.string.current_balance))
            },
            placeholder = {
                Text(stringResource(R.string._0_00))
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done, keyboardType = KeyboardType.Number
            ),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            modifier = Modifier.fillMaxWidth()
        )

        DatePickerField(date = uiState.accountData.dateOfCurrentBalance,
            label = stringResource(R.string.date_of_current_balance),
            supportingText = {
                if (uiState.dateOfCurrentBalanceError != null)
                    Text(uiState.dateOfCurrentBalanceError)
            },
            isError = uiState.dateOfCurrentBalanceError != null,
            onDateChanged = { year, month, day ->
                when {
                    day < 10 && month < 10 -> viewModel.updateCurrentDate("0$day.0$month.$year")

                    day < 10 -> viewModel.updateCurrentDate("0$day.$month.$year")

                    month < 10 -> viewModel.updateCurrentDate("$day.0$month.$year")

                    else -> viewModel.updateCurrentDate("$day.$month.$year")
                }
            })

        AccountTypeDropDownMenu(
            expanded = uiState.accountTypeMenuExpanded,
            isError = uiState.selectedAccountTypeError != null,
            onExpandedChange = { viewModel.updateAccountTypeExpanded(it) },
            onDismissRequest = { viewModel.updateAccountTypeExpanded(isExpanded = false) },
            onSelectType = { viewModel.updateAccountType(type = it) },
            selectedItem = uiState.accountData.selectedAccountType
        )

        Column(
            modifier = Modifier
                .selectableGroup()
                .padding(bottom = PADDING_BIG),
            verticalArrangement = Arrangement.spacedBy(PADDING_MED)
        ) {
            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(PADDING_SMALL),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        viewModel.updateBudgetType(budgetType = BudgetType.BudgetAccount)
                    }
            ) {
                RadioButton(
                    selected = uiState.accountData.selectedBudget == BudgetType.BudgetAccount,
                    onClick = {
                        viewModel.updateBudgetType(budgetType = BudgetType.BudgetAccount)
                    },
                    modifier =
                    Modifier.size(CHECKBOX_SIZE_SMALL)
                )
                Column {
                    Text(
                        text = BudgetType.BudgetAccount.type,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = BudgetType.BudgetAccount.desc,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(PADDING_SMALL),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        viewModel.updateBudgetType(budgetType = BudgetType.OffBudget)
                    }
            ) {
                RadioButton(
                    selected = uiState.accountData.selectedBudget == BudgetType.OffBudget,
                    onClick = { viewModel.updateBudgetType(budgetType = BudgetType.OffBudget) },
                    modifier =
                    Modifier.size(CHECKBOX_SIZE_SMALL)
                )
                Column {
                    Text(
                        text = BudgetType.OffBudget.type, style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = BudgetType.OffBudget.desc,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            if (uiState.selectedBudgetError != null) {
                Text(
                    text = uiState.selectedBudgetError,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.End)
                )
            }
        }

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                viewModel.createAccount {
                    if (it)
                        onSuccess(uiState.accountData)
                    else
                        Toast.makeText(
                            context,
                            "Check fields",
                            Toast.LENGTH_LONG
                        ).show()
                }
            }
        ) {
            Text(stringResource(R.string.create_account))
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun AccountTypeDropDownMenu(
    expanded: Boolean,
    isError: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onDismissRequest: () -> Unit,
    onSelectType: (AccountType) -> Unit,
    selectedItem: AccountType,
) {
    val listItems =
        arrayOf(AccountType.TYPE1, AccountType.TYPE2, AccountType.TYPE3, AccountType.TYPE4)

    ExposedDropdownMenuBox(
        expanded = expanded, onExpandedChange = onExpandedChange
    ) {
        OutlinedTextField(
            value = selectedItem.type,
            isError = isError,
            onValueChange = {},
            readOnly = true,
            label = { Text(text = stringResource(R.string.type)) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded,
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier.fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded, onDismissRequest = onDismissRequest
        ) {
            listItems.forEach { selectedOption ->
                DropdownMenuItem(
                    onClick = { onSelectType(selectedOption) },
                ) {
                    Text(selectedOption.type)
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DatePickerField(
    date: String, label: String,
    supportingText: @Composable (() -> Unit)? = null,
    isError: Boolean,
    onDateChanged: (Int, Int, Int) -> Unit
) {
    val mContext = LocalContext.current
    val mCalendar = Calendar.getInstance()
    val mYear = mCalendar.get(Calendar.YEAR)
    val mMonth = mCalendar.get(Calendar.MONTH)
    val mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()

    val mDatePickerDialog = DatePickerDialog(
        mContext, { _: DatePicker, mYearOfLife: Int, mMonthOfYear: Int, mDayOfMonth: Int ->
            onDateChanged(mYearOfLife, mMonthOfYear + 1, mDayOfMonth)
        }, mYear, mMonth, mDay
    )

    Box {
        OutlinedTextField(
            readOnly = true,
            value = date,
            onValueChange = {},
            supportingText = supportingText,
            isError = isError,
            label = { Text(label) },
            trailingIcon = {
                Icon(
                    Icons.Default.DateRange, contentDescription = "dateIcon"
                )
            },
            modifier = Modifier.fillMaxWidth(),
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .alpha(0f)
                .clickable(onClick = { mDatePickerDialog.show() }),
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun CreateAccountPreview() {
    AppTheme {
        EditAccountScreen(onCancel = {}, onSuccess = {})
    }
}