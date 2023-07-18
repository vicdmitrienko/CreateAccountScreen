package com.example.test.ui.common.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.test.ui.theme.PADDING_MED
import com.example.test.ui.theme.topAppBarContent

@Composable
fun BackAction(
    onBackClicked: () -> Unit
) {
    IconButton(onClick = onBackClicked) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = "Назад",
            tint = MaterialTheme.colors.topAppBarContent
        )
    }
}

@Composable
fun SearchAction(
    onSearchClicked: () -> Unit
) {
    IconButton(onClick = onSearchClicked) {
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = "Поиск",
            tint = MaterialTheme.colors.topAppBarContent
        )
    }
}

@Composable
fun CancelAction(
    onCancelClicked: () -> Unit
) {
    IconButton(onClick = onCancelClicked) {
        Icon(
            imageVector = Icons.Filled.Close,
            contentDescription = "Отменить",
            tint = MaterialTheme.colors.topAppBarContent
        )
    }
}

@Composable
fun AddAction(
    onAddClicked: () -> Unit
) {
    IconButton(
        onClick = onAddClicked
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Добавить",
            tint = MaterialTheme.colors.topAppBarContent
        )
    }
}

@Composable
fun EditAction(
    onEditClick: () -> Unit
) {
    IconButton(
        onClick = onEditClick
    ) {
        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = "Редактировать",
            tint = MaterialTheme.colors.topAppBarContent
        )
    }
}

@Composable
fun MoreVertAction(
    expanded: Boolean,
    onActionClick: () -> Unit,
    onListClick: () -> Unit,
    onSaveClick: () -> Unit
) {
    Box{
    IconButton(
        onClick = { onActionClick() }
    ) {
        Icon(
            imageVector = Icons.Default.MoreVert,
            contentDescription = "MoreVert",
            tint = MaterialTheme.colors.topAppBarContent
        )
    }
        DropdownMenu(expanded = expanded, onDismissRequest = { onActionClick() }) {
            Text(text = "Сохранить шаблон", modifier = Modifier
                .padding(PADDING_MED)
                .clickable { onSaveClick() })
            Divider()
            Text(text = "Список шаблонов",modifier = Modifier
                .padding(PADDING_MED)
                .clickable { onListClick() } )
        }
    }
}

@Preview
@Composable
fun PreviewIcons() {
    Column {
        BackAction {}
        SearchAction {}
        CancelAction {}
        AddAction {}
        EditAction {}
        MoreVertAction(expanded = false, onActionClick = {}, onListClick = {}, onSaveClick = {})
    }
}