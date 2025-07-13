package cz.mendelu.pef.xdostal8.transactionmanager.ui.elements

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import cz.mendelu.pef.xdostal8.transactionmanager.R

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun MySearchableDropdownMenu(
    options: List<String>,
    currentValue: String,
    onValueChange: (String) -> Unit,
    label: Int,

) {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(currentValue) }
    val keyboardController = LocalSoftwareKeyboardController.current



    Box( modifier = Modifier
        .fillMaxWidth()
       ){
        ExposedDropdownMenuBox(
            expanded = expanded,


            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            MyTextField(
                value = selectedText,
                label = stringResource(label),
                onValueChange = { selectedText = it },
                readOnly = false,
                error = "",
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor()
                    .fillMaxWidth()
            )

            val filteredOptions =
                options.filter { it.contains(selectedText, ignoreCase = true) }
            if (filteredOptions.isNotEmpty()) {
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {
                    }
                ) {
                    filteredOptions.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(text = item,
                            ) },
                            onClick = {
                                selectedText = item
                                expanded = false
                                onValueChange(item)
                                keyboardController?.hide()
                                Toast.makeText(context, "${context.getString(R.string.label_category)}: $item", Toast.LENGTH_SHORT).show()
                            }
                        )
                        Divider()
                    }
                }
            }
        }
    }
}
