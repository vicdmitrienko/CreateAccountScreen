package com.example.test.ui.common.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.test.ui.theme.AppTheme
import com.example.test.ui.theme.topAppBarBackground
import com.example.test.ui.theme.topAppBarContent

private val HEIGHT_APP_BAR = 56.dp

/**
 * Наиболее простой AppBar с титулом и стрелкой налево.
 */
@Composable
fun CommonAppBar(
    title: String,
    elevation: Dp = AppBarDefaults.TopAppBarElevation,
    onBackClick: (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {}
) {
    val backComp: (@Composable () -> Unit)? = if (onBackClick != null) {
        { BackAction(onBackClicked = onBackClick) }
    } else null

    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = backComp,
        actions = actions,
        elevation = elevation,
        backgroundColor = topAppBarBackground,
        contentColor = topAppBarContent
    )
}

@Composable
fun SearchListAppBar(
    text: String,
    elevation: Dp = AppBarDefaults.TopAppBarElevation,
    focusRequester: FocusRequester = remember { FocusRequester() },
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(HEIGHT_APP_BAR),
        elevation = elevation,
        color = topAppBarBackground
    ) {
        TextField(
            value = text,
            onValueChange = {
                onTextChange(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                // URL: https://stackoverflow.com/questions/64181930/request-focus-on-textfield-in-jetpack-compose
                .focusRequester(focusRequester),
            placeholder = {
                Text(
                    text = "Найти",
                    color = topAppBarContent,
                    modifier = Modifier.alpha(ContentAlpha.medium)
                )
            },
            textStyle = TextStyle(
                color = topAppBarContent,
                fontSize = MaterialTheme.typography.subtitle1.fontSize
            ),
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Найти",
                    tint = topAppBarContent,
                    modifier = Modifier.alpha(ContentAlpha.disabled)
                )
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        if (text.isEmpty()) {
                            onCloseClicked()
                        } else {
                            onTextChange("")
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Закрыть",
                        tint = topAppBarContent
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClicked(text)
                }
            ),
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = topAppBarContent,
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                backgroundColor = Color.Transparent
            )
        )
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun AppBarPreview() {
    AppTheme {
        CommonAppBar(
            title = "Заголовок",
            onBackClick = {}
        )
    }
}

@Preview
@Composable
private fun AppBarSearchPreview() {
    AppTheme {
        SearchListAppBar(
            text = "",
            onTextChange = {},
            onCloseClicked = { },
            onSearchClicked = { }
        )
    }
}
