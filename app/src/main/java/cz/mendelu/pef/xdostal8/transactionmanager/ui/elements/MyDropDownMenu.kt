package cz.mendelu.pef.xdostal8.transactionmanager.ui.elements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.toSize

@Composable
fun MyDropDownMenu(
    options: List<String>,
    currentValue: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: Int,
    iconDescription: Int
) {
    var expanded by remember { mutableStateOf(false) }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    Box {
        MyTextField(value = currentValue,
            label = stringResource(label),
            onValueChange = onValueChange,
            error = "",
            readOnly = true,
            modifier = modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    textFieldSize = coordinates.size.toSize()
                },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = stringResource(iconDescription),
                    modifier = Modifier.clickable { expanded = true },
                )
            })

        DropdownMenu(modifier = Modifier.width(with(LocalDensity.current) { textFieldSize.width.toDp() }),
            expanded = expanded,
            onDismissRequest = { expanded = false }) {
            options.forEach { type ->
                DropdownMenuItem(text = {
                    Text(
                        text = type,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.width(with(LocalDensity.current) { textFieldSize.width.toDp() }),
                    )
                }, onClick = {
                    onValueChange(type)
                    expanded = false
                })
                Divider()
            }
        }
    }
}