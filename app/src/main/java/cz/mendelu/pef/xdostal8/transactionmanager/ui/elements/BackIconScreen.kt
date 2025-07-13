package cz.mendelu.pef.xdostal8.transactionmanager.ui.elements

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cz.mendelu.pef.xdostal8.transactionmanager.ui.theme.basicMargin
import cz.mendelu.pef.xdostal8.transactionmanager.ui.theme.getBasicTextColor
import cz.mendelu.pef.xdostal8.transactionmanager.ui.theme.getTintColor
import cz.mendelu.pef.xdostal8.transactionmanager.R
import cz.mendelu.pef.xdostal8.transactionmanager.ui.theme.titleLarge

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackIconScreen(
    appBarTitle: String,
    disablePadding: Boolean = false,
    onBackClick: () -> Unit,
    drawFullScreenContent: Boolean = false,
    actions: @Composable RowScope.() -> Unit = {},
    content: @Composable (paddingValues: PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentWidth(align = Alignment.CenterHorizontally)
                        ) {
                            Text(
                                text = appBarTitle,
                                style = titleLarge(),
                                color = getBasicTextColor(),
                                modifier = Modifier
                                    .padding(start = 0.dp)
                                    .weight(1.5f)
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            onBackClick()
                        }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = stringResource(R.string.go_back),
                                tint = getTintColor()
                            )
                        }

                    },
                    actions = actions
                )
                Divider() // Line under the TopAppBar
            }
        }
    ) {
        if (!drawFullScreenContent) {
            LazyColumn(modifier = Modifier.padding(it)) {
                item {
                    Column(
                        verticalArrangement = Arrangement.Top,
                        modifier = Modifier
                            .padding(if (!disablePadding) basicMargin() else 0.dp)
                    ) {
                        content(it)
                    }
                }
            }
        } else {
            content(it)
        }
    }
}