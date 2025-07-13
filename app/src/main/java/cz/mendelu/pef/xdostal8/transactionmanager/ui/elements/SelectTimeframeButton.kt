package cz.mendelu.pef.xdostal8.transactionmanager.ui.elements

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha

@Composable
fun SelectTimeframeButton(
    value: String?, onClick: () -> Unit, enabled: Boolean
) {

    TextButton(
        onClick = onClick, modifier = Modifier.alpha(if (enabled) 1f else 0.5f)
    ) {
        Text(
            text = value ?: "",
        )
    }


}