package cz.mendelu.pef.xdostal8.transactionmanager.ui.screens.stats

import android.annotation.SuppressLint
import android.graphics.Bitmap.CompressFormat
import java.util.*
import android.graphics.Bitmap
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.graphics.applyCanvas
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import cz.mendelu.pef.xdostal8.transactionmanager.R
import cz.mendelu.pef.xdostal8.transactionmanager.extensions.writeBitmap
import cz.mendelu.pef.xdostal8.transactionmanager.model.Transaction
import cz.mendelu.pef.xdostal8.transactionmanager.navigation.INavigationRouter
import cz.mendelu.pef.xdostal8.transactionmanager.ui.elements.*
import cz.mendelu.pef.xdostal8.transactionmanager.ui.theme.*
import org.koin.androidx.compose.getViewModel
import java.io.File
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.highlight.Highlight


@SuppressLint("MutableCollectionMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatsScreen(
    navigation: INavigationRouter,
    viewModel: StatsScreenViewModel = getViewModel(),
) {

    val transactions = remember { mutableStateListOf<Transaction>() }

    val availableCategories = rememberSaveable { mutableStateOf(HashSet<String>()) }


    val data: StatsScreenData by remember {
        mutableStateOf(viewModel.data)
    }

    val exportData: StatsScreenExportData by remember {
        mutableStateOf(viewModel.exportData)
    }

    var capturingViewBounds by remember { mutableStateOf<Rect?>(null) }

    viewModel.statsScreenUIState.value.let {
        when (it) {
            StatsScreenUIState.Default -> {
                LaunchedEffect(it) {
                    viewModel.loadCategories()
                }
            }

            is StatsScreenUIState.Success -> {
                transactions.clear()
                transactions.addAll(it.transactions)
                viewModel.setSelectedCurrency()
                data.loading = false
            }
            StatsScreenUIState.ManagerScreenSelected -> {
                LaunchedEffect(it) {
                    navigation.navigateBack()
                }
            }
            StatsScreenUIState.TimeframeChanged -> {
                viewModel.loadTransactions()
            }
            StatsScreenUIState.CategoryChanged -> {
                viewModel.loadTransactions()

            }
            StatsScreenUIState.TransactionTypeChanged -> {
                viewModel.loadTransactions()

            }
            StatsScreenUIState.CreateImage -> {
                exportData.createImage = true
            }
            is StatsScreenUIState.CategoriesLoaded -> {
                availableCategories.value.clear()
                availableCategories.value.addAll(it.categories)
                data.loading = false
                viewModel.loadTransactions()
            }
            StatsScreenUIState.ImageSaved -> {
                exportData.createImage = false
            }

            StatsScreenUIState.ExportSettingsChanged -> {
            }
        }
    }

    Scaffold(topBar = {
        TopAppBar(colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = getCurrentSecondaryColor(),
            titleContentColor = getBarTitleColor()
        ), title = {
            Row {
                SelectTimeframeButton(
                    value = stringResource(R.string.title_week),
                    onClick = { viewModel.changeTimeframe("week") },
                    enabled = data.timeframe == "week"
                )
                SelectTimeframeButton(
                    value = stringResource(R.string.title_month),
                    onClick = { viewModel.changeTimeframe("month") },
                    enabled = data.timeframe == "month"
                )
                SelectTimeframeButton(
                    value = stringResource(R.string.title_year),
                    onClick = { viewModel.changeTimeframe("year") },
                    enabled = data.timeframe == "year"
                )
            }
        }, actions = {
            FilledIconButton(onClick = {
                navigation.navigateToExport()
            }) {
                Icon(
                    Icons.Outlined.Share,
                    contentDescription = stringResource(R.string.description_export_button)
                )
            }
        })
    },


        floatingActionButton = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(Modifier.padding(horizontal = 75.dp)) {
                    Switch(checked = data.spendingsSelected,
                        onCheckedChange = { viewModel.transactionTypeSwitched() })
                }

                Box {
                    Column(
                        modifier = Modifier.width(200.dp)
                    ) {


                        MySearchableDropdownMenu(
                            options = availableCategories.value.toList(),
                            currentValue = data.categorySelected,
                            onValueChange = { viewModel.onCategoryChange(it) },
                            label = R.string.label_category,
                        )
                    }
                }

            }
        }, bottomBar = {
            MyBottomBar(selectedItem = data.selectedScreen,
                onClick = { viewModel.reselectScreen(it) })
        }) {
        StatsScreenContent(
            modifier = Modifier.onGloballyPositioned { it1 ->
                capturingViewBounds = it1.boundsInRoot()
            },
            paddingValues = it,
            actions = viewModel,
            transactions = transactions,
            exportData = viewModel.exportData,
            isSpending = data.spendingsSelected,
            currency = data.currency ?: stringResource(R.string.currency),
            loading = data.loading
        )
    }
}

@Composable
fun StatsScreenContent(
    modifier: Modifier,
    transactions: List<Transaction>,
    paddingValues: PaddingValues,
    actions: StatsScreenActions,
    exportData: StatsScreenExportData,
    isSpending: Boolean,
    loading: Boolean,
    currency: String
) {

    if (!loading) {
        Column(
            modifier = modifier.padding(
                top = 80.dp,
                bottom = paddingValues.calculateBottomPadding(),
            ), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(halfMargin()))
            MyPieChart(
                transactions = transactions, isSpending = isSpending, exportData, actions, currency
            )
        }
    } else {
        LoadingScreen()
    }
}

@Composable
fun MyPieChart(
    transactions: List<Transaction>,
    isSpending: Boolean,
    exportData: StatsScreenExportData,
    actions: StatsScreenActions,
    currency: String
) {


    val view = LocalView.current
    val context = LocalContext.current

    val position = remember { mutableStateOf(Offset.Zero) }
    val composableSize = remember { mutableStateOf(IntSize.Zero) }


    Column(modifier = Modifier.onGloballyPositioned { coordinates ->
        position.value = coordinates.localToRoot(Offset.Zero)
        composableSize.value = coordinates.size

    }, horizontalAlignment = Alignment.CenterHorizontally) {

        if (exportData.createImage) {

            val format = actions.getFormat()
            val quality = actions.getQuality()

            val handler = Handler(Looper.getMainLooper())
            handler.postDelayed({
                val bmp = Bitmap.createBitmap(
                    view.width, view.height, Bitmap.Config.ARGB_8888
                ).applyCanvas {
                    view.draw(this)
                }
                bmp.let {
                    File(context.filesDir, "screenshot.png").writeBitmap(
                        bmp,
                        if (format == "PNG") CompressFormat.PNG else CompressFormat.JPEG,
                        quality
                    )
                }
                val bitmap: Bitmap = bmp

                val offsetX = position.value.x.toInt()
                val offsetY = position.value.y.toInt()
                val composableWidth = composableSize.value.width
                val composableHeight = composableSize.value.height

                val croppedBitmap =
                    Bitmap.createBitmap(bitmap, offsetX, offsetY, composableWidth, composableHeight)


                actions.saveBitmapToGallery(context = context, bitmap = croppedBitmap)
            }, 1000)
        }
        val type: String = if (isSpending) {
            stringResource(R.string.type_spending)
        } else {
            stringResource(R.string.type_income)
        }
        TextButton(
            onClick = { actions.createImage() },
        ) {
            Text(
                text = type, style = titleLarge(), color = getBarTitleColor()
            )
        }
        AndroidView(modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 70.dp),
            factory = { context ->
                val pieChart = PieChart(context)

                val entries = transactions.mapIndexed { _, transaction ->
                    PieEntry(transaction.amount.toFloat(), transaction.text)
                }

                val colors = actions.getColors(entries.size)

                val whiteInt = Color.White.toArgb()

                val dataSet = PieDataSet(entries, null).apply {
                    setColors(colors)
                    valueTextSize = 16f
                    valueTextColor = whiteInt
                }
                val pieData = PieData(dataSet)

                pieChart.data = pieData
                pieChart.invalidate()

                pieChart.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
                    override fun onValueSelected(e: Entry?, h: Highlight?) {
                        val pieEntry = e as PieEntry
                        Toast.makeText(
                            context,
                            "${pieEntry.label} - ${pieEntry.value} $currency",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onNothingSelected() {
                    }
                })

                pieChart
            }) { chart ->
            try {
                // Update chart data
                val entries = transactions.mapIndexed { _, transaction ->
                    PieEntry(transaction.amount.toFloat(), transaction.text)
                }

                val dataSet = PieDataSet(entries, "Label").apply {
                    colors = actions.getColors(entries.size)
                }
                val totalAmount = transactions.sumOf { it.amount }


                dataSet.valueTextColor = Color.White.toArgb()
                dataSet.valueTextSize = 10F

                chart.data = PieData(dataSet)
                chart.notifyDataSetChanged()
                chart.invalidate()
                chart.setDrawCenterText(true)

                chart.centerText = if (currency != "$") {
                    "$totalAmount $currency"
                } else {
                    "$currency $totalAmount"
                }

                chart.legend.isEnabled = false
                chart.description.isEnabled = false


            } catch (ex: Exception) {
                Log.d("TAG", "MyPieChart: $ex")
            }
        }
    }
}
