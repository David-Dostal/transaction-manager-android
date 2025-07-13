package cz.mendelu.pef.xdostal8.transactionmanager.ui.elements

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import cz.mendelu.pef.xdostal8.transactionmanager.R
import cz.mendelu.pef.xdostal8.transactionmanager.ui.theme.getCurrentSecondaryColor


@Composable
fun MyBottomBar(
    selectedItem: Int,
    onClick: (Int) -> Unit,
) {
    val items = listOf(stringResource(R.string.manager), stringResource(R.string.stats))
    val icons = listOf(Icons.Default.ShoppingCart, Icons.Default.DateRange)

    NavigationBar(containerColor = getCurrentSecondaryColor()) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(icon = {
                Icon(
                    imageVector = icons[index], contentDescription = null
                )
            },
                label = { Text(item) },
                selected = selectedItem == index,
                onClick = { onClick(index) })
        }
    }
}