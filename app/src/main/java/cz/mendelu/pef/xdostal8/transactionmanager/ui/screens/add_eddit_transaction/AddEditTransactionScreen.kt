package cz.mendelu.pef.xdostal8.transactionmanager.ui.screens.add_eddit_transaction

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.DatePicker
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import cz.mendelu.pef.xdostal8.transactionmanager.ui.theme.basicMargin
import cz.mendelu.pef.xdostal8.transactionmanager.ui.theme.getTintColor
import cz.mendelu.pef.xdostal8.transactionmanager.ui.theme.roundedCorner
import cz.mendelu.pef.xdostal8.transactionmanager.R
import cz.mendelu.pef.xdostal8.transactionmanager.navigation.INavigationRouter
import cz.mendelu.pef.xdostal8.transactionmanager.ui.elements.*
import cz.mendelu.pef.xdostal8.transactionmanager.utils.DateUtils
import org.koin.androidx.compose.getViewModel
import java.util.Calendar

@Composable
fun AddEditTransactionScreen(
    navigation: INavigationRouter,
    viewModel: AddEditTransactionViewModel = getViewModel(),
    id: Long?
) {
    viewModel.transactionId = id
    var data: AddEditScreenData by remember {
        mutableStateOf(viewModel.data)
    }

    viewModel.addEditTransactionUIState.value.let {
        when (it) {
            AddEditTransactionUIState.Default -> {

            }
            AddEditTransactionUIState.TransactionDeleted -> {
                LaunchedEffect(it) {
                    navigation.navigateBack()
                }
            }
            AddEditTransactionUIState.TransactionSaved -> {
                LaunchedEffect(it) {
                    data.loading = false
                    navigation.navigateBack()
                }

            }
            AddEditTransactionUIState.TransactionChanged -> {
                data = viewModel.data
                viewModel.addEditTransactionUIState.value = AddEditTransactionUIState.Default
            }
            AddEditTransactionUIState.Loading -> {
                LaunchedEffect(it) {
                    viewModel.initTransaction()
                }
            }
        }

    }

    BackIconScreen(
        appBarTitle = if (viewModel.transactionId == null) stringResource(R.string.add_transaction) else stringResource(
            R.string.edit_transaction
        ),
        onBackClick = {
            navigation.navigateBack()
        },
        actions = {
            if (viewModel.transactionId != null) {
                IconButton(onClick = {
                    viewModel.deleteTransaction()
                }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        tint = getTintColor()
                    )
                }
            }
        },
    ) {
        AddEditTransactionScreenContent(
            actions = viewModel, data = data
        )
    }
}

@Composable
fun AddEditTransactionScreenContent(
    actions: AddEditTransactionActions,
    data: AddEditScreenData,
) {
    if (!data.loading) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(
                onClick = { actions.onIsIncomeChange(true) },
                colors = ButtonDefaults.outlinedButtonColors(contentColor = if (data.transaction.isIncome) Color.Green else Color.Gray)
            ) {
                Text(text = stringResource(R.string.type_income))
            }
            Switch(checked = data.transaction.isIncome,
                onCheckedChange = { actions.onIsIncomeChange(it) })
            OutlinedButton(
                onClick = { actions.onIsIncomeChange(false) },
                colors = ButtonDefaults.outlinedButtonColors(contentColor = if (data.transaction.isIncome) Color.Gray else Color.Red)
            ) {
                Text(text = stringResource(R.string.type_spending))
            }
        }
        Spacer(modifier = Modifier.height(basicMargin()))

        MyTextField(
            value = data.transaction.text,
            label = stringResource(R.string.hint_text),
            onValueChange = {
                actions.onTextChange(it)
            },
            error = if (data.transactionTextError != null) stringResource(id = data.transactionTextError!!) else ""
        )


        MyNumberField(
            value = data.rawAmount,
            hint = stringResource(R.string.hint_amount),
            onValueChange = {
                actions.onAmountChange(it)
            },
            error = if (data.transactionAmountError != null) stringResource(id = data.transactionAmountError!!) else ""
        )
        Spacer(modifier = Modifier.height(basicMargin()))

        MyTextField(
            value = data.transaction.category,
            label = stringResource(R.string.hint_category),
            onValueChange = {
                actions.onCategoryChange(it)
            },
            error = if (data.transactionCategoryError != null) stringResource(id = data.transactionCategoryError!!) else ""
        )

        val calendar = Calendar.getInstance()
        data.transaction.date.let {
            calendar.timeInMillis = it
        }
        val y = calendar.get(Calendar.YEAR)
        val m = calendar.get(Calendar.MONTH)
        val d = calendar.get(Calendar.DAY_OF_MONTH)
        val datePickerDialog = DatePickerDialog(
            LocalContext.current, { _: DatePicker, year: Int, month: Int, day: Int ->
                actions.onDateChange(DateUtils.getUnixTime(year, month, day))
            }, y, m, d
        )

        InfoElement(value = DateUtils.getDateString(data.transaction.date),
            label = stringResource(R.string.date),
            leadingIcon = R.drawable.ic_event_24,
            onClick = {
                datePickerDialog.show()
            },
            onClearClick = {

                @Suppress("NAME_SHADOWING") val calendar = Calendar.getInstance()
                actions.onDateChange(calendar.timeInMillis)
            })
        Spacer(modifier = Modifier.height(basicMargin()))

        SelectImage(
            context = LocalContext.current,
            actions = actions,
            currentImageString = data.transaction.image
        )
        Spacer(modifier = Modifier.height(basicMargin()))
        Spacer(modifier = Modifier.height(basicMargin()))



        OutlinedButton(
            modifier = Modifier.fillMaxWidth(), onClick = {
                actions.saveTransaction()

            }, shape = RoundedCornerShape(roundedCorner()), colors = ButtonDefaults.buttonColors(
                contentColor = Color.White, containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text(text = stringResource(R.string.save))
        }

    } else {
        LoadingScreen()
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun DisplayImageFromGallery(
    imageUri: Uri, onUnselectImage: () -> Unit
) {
    Spacer(modifier = Modifier.height(basicMargin()))

    Image(
        painter = rememberImagePainter(imageUri),
        contentDescription = "Image from gallery",
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        contentScale = ContentScale.FillWidth
    )

    Spacer(modifier = Modifier.height(basicMargin()))

    OutlinedButton(
        onClick = { onUnselectImage() },
        shape = RoundedCornerShape(roundedCorner()),
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.White, containerColor = MaterialTheme.colorScheme.error
        )
    ) {
        Text(text = stringResource(R.string.unselect_image_button))
    }
}

@Composable
fun SelectImage(context: Context, actions: AddEditTransactionActions, currentImageString: String) {
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    if (currentImageString != "None") {
        selectedImageUri = Uri.parse(currentImageString)
    }

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.OpenDocument()) { uri: Uri? ->
            uri?.let {
                val contentResolver = context.contentResolver
                val takeFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                contentResolver.takePersistableUriPermission(it, takeFlags)
                selectedImageUri = it
                actions.onImageChanged(uri)
            }
        }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (selectedImageUri == null) {
            OutlinedButton(
                onClick = {
                    launcher.launch(arrayOf("image/*"))
                },
                shape = RoundedCornerShape(roundedCorner()),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White, containerColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Text(text = stringResource(R.string.select_image_button))
            }
        }

        selectedImageUri?.let { uri ->
            DisplayImageFromGallery(uri) {
                selectedImageUri = null // Unselect the image
                actions.onImageChanged(null) // Notify the parent composable about the unselect event
            }
        }
    }
}