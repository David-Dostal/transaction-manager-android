package cz.mendelu.pef.xdostal8.transactionmanager.ui.elements

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cz.mendelu.pef.xdostal8.transactionmanager.ui.theme.smallMargin

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyNumberField(
    value: String, hint: String, onValueChange: (String) -> Unit, error: String
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = hint) },
        maxLines = 1,
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 0.dp, 0.dp, 0.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )

    if (error.isNotEmpty()) {
        Text(
            text = error,
            modifier = Modifier
                .alpha(if (error.isNotEmpty()) 100f else 0f)
                .fillMaxWidth()
                .padding(0.dp, 0.dp, 0.dp, smallMargin()),
            color = Color.Red,
            textAlign = TextAlign.Start,
            fontSize = 11.sp
        )
    }

}