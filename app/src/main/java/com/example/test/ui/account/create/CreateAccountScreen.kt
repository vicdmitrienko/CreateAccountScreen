package com.example.test.ui.account.create

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.TextField
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.test.BudgetType
import com.example.test.R
import com.example.test.ui.common.components.CommonAppBar
import com.example.test.ui.theme.PADDING_BIG
import com.example.test.ui.theme.PADDING_MED
import com.example.test.ui.theme.AppTheme
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateAccountScreen() {

    val thisViewModel: CreateAccountViewModel = viewModel()
    val uiState by thisViewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            CommonAppBar(stringResource(R.string.create_account))
        },
        content = { padding ->
            CreateAccountBody(
                padding = padding,
                uiState = uiState,
                viewModel = thisViewModel
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CreateAccountBody(
    padding: PaddingValues,
    uiState: CreateAccountViewModel.UiState,
    viewModel: CreateAccountViewModel
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .padding(padding)
            .padding(vertical = PADDING_MED, horizontal = PADDING_BIG)
            .verticalScroll(state = rememberScrollState())
    ) {

        OutlinedTextField(
            value = uiState.name,
            onValueChange = { viewModel.updateUserName(it) },
            label = {
                Text(stringResource(R.string.name))
            },

            // Вывод сообщения об ошибке
            supportingText = {
                Text("Текст ошибки")
            },
            isError = true,
            
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            }),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = PADDING_BIG)
        )

        OutlinedTextField(value = uiState.currentBalance,
            onValueChange = { viewModel.updateCurrentBalance(it) },
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
            modifier = Modifier.fillMaxWidth())
        Text(
            stringResource(R.string.balance_hint),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        if (uiState.currentBalance.isEmpty()) Text(
            text = stringResource(R.string.empty_balance),
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.align(Alignment.End)
        )

        DatePickerField(date = uiState.dateOfCurrentBalance,
            label = stringResource(R.string.date_of_current_balance),
            onDateChanged = { year, month, day ->
                when {
                    day < 10 && month < 10 -> viewModel.updateCurrentDate("0$day.0$month.$year")

                    day < 10 -> viewModel.updateCurrentDate("0$day.$month.$year")

                    month < 10 -> viewModel.updateCurrentDate("$day.0$month.$year")

                    else -> viewModel.updateCurrentDate("$day.$month.$year")
                }
            })
        if (uiState.dateOfCurrentBalance.isEmpty()) Text(
            text = stringResource(R.string.choose_date),
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.align(Alignment.End)
        )

        AccountTypeDropDownMenu(
            expanded = uiState.accountTypeMenuExpanded,
            onExpandedChange = { viewModel.updateAccountTypeExpanded(it) },
            onDismissRequest = { viewModel.updateAccountTypeExpanded(isExpanded = false) },
            onSelectType = { viewModel.updateAccountType(type = it) },
            selectedItem = uiState.selectedAccountType
        )
        if (uiState.selectedAccountType == "Select an Account Type") Text(
            text = stringResource(R.string.select_account_type),
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.align(Alignment.End)
        )

        //TODO: У этих элементов ввода есть неприятная особенность -
        // пользователь будет хотеть тыкать в текст! И ожидать, что установится галочка.
        Column(
            modifier = Modifier
                .selectableGroup()
                .padding(bottom = PADDING_BIG)
        ) {
            Row(
                verticalAlignment = Alignment.Top
            ) {
                //FIXME: Как поднять радио-кнопки на уровень первой строки?
                RadioButton(
                    selected = uiState.selectedBudget == BudgetType.BudgetAccount,
                    onClick = { viewModel.updateBudgetType(budgetType = BudgetType.BudgetAccount) }
                )
                Column {
                    Text(
                        text = "Budget Account",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "This account should affect my budget",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            Row(
                verticalAlignment = Alignment.Top
            ) {
                RadioButton(
                    selected = uiState.selectedBudget == BudgetType.OffBudget,
                    onClick = { viewModel.updateBudgetType(budgetType = BudgetType.OffBudget) }
                )
                Column {
                    Text(
                        text = "Off-Budget",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "This account should not affect my budget",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            if (uiState.selectedBudget == null) {
                Text(
                    text = stringResource(R.string.select_budget_affect),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(start = PADDING_BIG)
                )
            }
        }

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {/*TODO*/ }
        ) {
            Text(stringResource(R.string.create_account))
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AccountTypeDropDownMenu(
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onDismissRequest: () -> Unit,
    onSelectType: (String) -> Unit,
    selectedItem: String,
) {
    val listItems = arrayOf("Account Type 1", "Account Type 2", "Account Type 3", "Account Type 4")

    ExposedDropdownMenuBox(
        expanded = expanded, onExpandedChange = onExpandedChange
    ) {
        TextField(value = selectedItem,
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
                    Text(selectedOption)
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerField(
    date: String, label: String, onDateChanged: (Int, Int, Int) -> Unit
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
        OutlinedTextField(readOnly = true,
            value = date,
            onValueChange = {},
            label = { Text(label) },
            trailingIcon = {
                Icon(
                    Icons.Default.DateRange, contentDescription = "dateIcon"
                )
            },
            modifier = Modifier.fillMaxWidth()
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
fun CreateAccountPreview() {
    AppTheme {
        CreateAccountScreen()
    }
}