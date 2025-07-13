package cz.mendelu.pef.xdostal8.transactionmanager.ui.screens.transaction_list

import android.app.DatePickerDialog
import android.net.Uri
import android.widget.DatePicker
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import cz.mendelu.pef.xdostal8.transactionmanager.R
import cz.mendelu.pef.xdostal8.transactionmanager.model.Transaction
import cz.mendelu.pef.xdostal8.transactionmanager.navigation.INavigationRouter
import cz.mendelu.pef.xdostal8.transactionmanager.ui.elements.LoadingScreen
import cz.mendelu.pef.xdostal8.transactionmanager.ui.elements.MyBottomBar
import cz.mendelu.pef.xdostal8.transactionmanager.ui.elements.PlaceHolderScreen
import cz.mendelu.pef.xdostal8.transactionmanager.ui.elements.SelectedDateField
import cz.mendelu.pef.xdostal8.transactionmanager.ui.theme.getBarTitleColor
import cz.mendelu.pef.xdostal8.transactionmanager.ui.theme.getCurrentPrimaryColor
import cz.mendelu.pef.xdostal8.transactionmanager.ui.theme.getCurrentSecondaryColor
import cz.mendelu.pef.xdostal8.transactionmanager.ui.theme.getTintColor
import cz.mendelu.pef.xdostal8.transactionmanager.utils.DateUtils
import org.koin.androidx.compose.getViewModel
import java.util.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionListScreen(
    navigation: INavigationRouter, viewModel: TransactionListViewModel = getViewModel()
) {

    val transactions = viewModel.transactions.value


    var data: TransactionListScreenData by remember {
        mutableStateOf(viewModel.data)
    }
    var showPlaceholder: Boolean by rememberSaveable { mutableStateOf(false) }

    val calendar = Calendar.getInstance()
    data.date.let {
        if (it != null) {
            calendar.timeInMillis = it
        }
    }
    val y = calendar.get(Calendar.YEAR)
    val m = calendar.get(Calendar.MONTH)
    val d = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        LocalContext.current, { _: DatePicker, year: Int, month: Int, day: Int ->
            viewModel.changeSelectedDate(DateUtils.getUnixTime(year, month, day))
        }, y, m, d
    )



    viewModel.transactionListUIState.value.let {
        when (it) {
            TransactionListUIState.Default -> {
            }
            TransactionListUIState.StatsScreenSelected -> {
                navigation.navigateToStatsScreen()
                viewModel.reselectScreen(0)
                viewModel.transactionListUIState.value = TransactionListUIState.Loading
            }
            is TransactionListUIState.Success -> {
                showPlaceholder = it.transactions.isEmpty()
                viewModel.setSelectedCurrency()
                data.loading = false
            }
            TransactionListUIState.DateChanged -> {
                data = viewModel.data

                data.loading = false


            }
            TransactionListUIState.Loading -> {


                data.loading = true
                viewModel.setSelectedCurrency()
                viewModel.loadTransactions()
            }
        }
    }

    if (!data.loading) {
        Scaffold(topBar = {
            TopAppBar(colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = getCurrentSecondaryColor(),
                titleContentColor = getBarTitleColor()
            ), title = {
                SelectedDateField(
                    value = data.date?.let { DateUtils.getDateString(it) },
                    onClick = {
                        datePickerDialog.show()
                    },


                    )
            }, actions = {
                FilledIconButton(onClick = {
                    navigation.navigateToSettingsScreen()
                    viewModel.loadTransactions()


                }) {
                    Icon(
                        Icons.Outlined.Settings,
                        contentDescription = stringResource(R.string.settings_description)
                    )
                }
            })
        }, floatingActionButton = {
            FloatingActionButton(containerColor = getCurrentSecondaryColor(),
                onClick = { navigation.navigateToAddEditTransactionScreen(-1L) }) {

                Icon(
                    Icons.Outlined.Create,
                    tint = getTintColor(),
                    contentDescription = stringResource(R.string.add_transaction_description)
                )
            }
        }, bottomBar = {
            MyBottomBar(
                selectedItem = data.selectedScreen,
                onClick = { viewModel.reselectScreen(it) })
        }) {
            TransactionListScreenContent(
                paddingValues = it,
                transactions = transactions,
                navigation = navigation,
                showPlaceholder = showPlaceholder,
                actions = viewModel,
                currency = data.currency ?: stringResource(R.string.currency)
            )
        }
    } else {
        LoadingScreen()
    }
}

@Composable
fun TransactionListScreenContent(
    paddingValues: PaddingValues,
    transactions: List<Transaction>,
    navigation: INavigationRouter,
    showPlaceholder: Boolean,
    actions: TransactionListActions,
    currency: String
) {


    if (!showPlaceholder) {
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            transactions.forEach {
                item(key = it.id) {
                    TransactionRow(
                        transaction = it,
                        onRowClick = {

                            navigation.navigateToAddEditTransactionScreen(it.id)
                            actions.loadTransactions()
                        },
                        currency = currency,
                    )

                }
            }
        }
    } else {
        PlaceHolderScreen(
            image = R.drawable.placeholder_no_tasks,
            text = stringResource(R.string.no_transactions_text)
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalCoilApi::class)
@Composable
fun TransactionRow(
    transaction: Transaction, onRowClick: () -> Unit, currency: String
) {
    val text: String = if (currency != "$") {
        "${transaction.amount} $currency"
    } else {
        "$currency ${transaction.amount}"
    }
    Row(modifier = Modifier.clickable(onClick = onRowClick)) {
        ListItem(supportingText = {
            Text(
                text = text,
                fontWeight = FontWeight.Bold,
                color = if (transaction.isIncome) Color.Green else Color.Red,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )
        }, headlineText = {
            Text(
                text = transaction.text,

                )
        }, leadingContent = {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(getCurrentPrimaryColor(), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                if (transaction.image == "None") {
                    Text(
                        text = transaction.text.first().toString().uppercase(Locale.getDefault()),
                        fontSize = 20.sp,
                        color = Color.White
                    )
                } else {
                    val painter = rememberImagePainter(Uri.parse(transaction.image))
                    Image(
                        painter = painter,
                        contentDescription = stringResource(R.string.image_description),
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                            .scale(1.5f) // zoom

                    )
                }
            }
        })
    }
}