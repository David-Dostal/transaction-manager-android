package cz.mendelu.pef.xdostal8.transactionmanager.ui.screens.stats

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.os.Environment
import android.provider.MediaStore
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import cz.mendelu.pef.xdostal8.transactionmanager.architecture.BaseViewModel
import cz.mendelu.pef.xdostal8.transactionmanager.database.ITransactionsRepository
import cz.mendelu.pef.xdostal8.transactionmanager.datastore.IDataStoreRepository
import kotlinx.coroutines.launch
import java.io.File
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*


class StatsScreenViewModel(
    private val repository: ITransactionsRepository,
    private val dataStoreRepository: IDataStoreRepository
) : BaseViewModel(), StatsScreenActions {

    var exportData: StatsScreenExportData = StatsScreenExportData()

    var data: StatsScreenData = StatsScreenData()

    val statsScreenUIState: MutableState<StatsScreenUIState> =
        mutableStateOf(StatsScreenUIState.Default)


    override fun reselectScreen(screen: Int) {
        launch {
            data.selectedScreen = screen
            if (screen == 0) {
                statsScreenUIState.value = StatsScreenUIState.ManagerScreenSelected
            }
        }
    }

    override fun loadCategories() {
        data.loading = true
        launch {
            repository.getAllCategories().collect { categories ->
                statsScreenUIState.value = StatsScreenUIState.CategoriesLoaded(categories)
            }
        }
    }


    override fun saveImage(image: Bitmap) {
        data.graphImage = image
        statsScreenUIState.value = StatsScreenUIState.ImageSaved
    }

    override fun createImage() {
        statsScreenUIState.value = StatsScreenUIState.CreateImage
    }


    override fun loadTransactions() {
        data.loading = true
        launch {
            val timeframeInMillis = when (data.timeframe) {
                "week" -> data.date - 6 * 24 * 60 * 60 * 1000
                "month" -> getStartOfMonthInMillis(data.date)
                "year" -> getStartOfYearInMillis(data.date)
                else -> throw IllegalArgumentException("Invalid timeframe")
            }


            repository.getTransactionsWithinTimeframe(
                startTimestamp = data.date, endTimestamp = timeframeInMillis
            ).collect { transactions ->
                val filteredTransactions = transactions.filter { transaction ->
                    // Check if the transaction matches the selected category and boolean
                    if (data.categorySelected == "") {
                        transaction.isIncome != data.spendingsSelected
                    } else {
                        transaction.category == data.categorySelected && transaction.isIncome != data.spendingsSelected
                    }
                }

                statsScreenUIState.value = StatsScreenUIState.Success(filteredTransactions)
            }
        }
    }


    private fun getStartOfMonthInMillis(currentDateInMillis: Long): Long {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = currentDateInMillis
            set(Calendar.DAY_OF_MONTH, 1)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        return calendar.timeInMillis
    }

    private fun getStartOfYearInMillis(currentDateInMillis: Long): Long {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = currentDateInMillis
            set(Calendar.MONTH, Calendar.JANUARY)
            set(Calendar.DAY_OF_MONTH, 1)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        return calendar.timeInMillis
    }

    override fun changeTimeframe(timeframe: String) {
        data.timeframe = timeframe
        data.categorySelected = ""
        statsScreenUIState.value = StatsScreenUIState.TimeframeChanged

    }

    override fun transactionTypeSwitched() {
        data.spendingsSelected = !data.spendingsSelected
        statsScreenUIState.value = StatsScreenUIState.TransactionTypeChanged
    }

    override fun onCategoryChange(category: String) {
        data.categorySelected = category
        statsScreenUIState.value = StatsScreenUIState.TransactionTypeChanged
    }

    override fun changeQuality(quality: Int) {
        exportData.quality = quality
        statsScreenUIState.value = StatsScreenUIState.ExportSettingsChanged
    }

    override fun changeFormat(format: String) {
        exportData.format = format
        statsScreenUIState.value = StatsScreenUIState.ExportSettingsChanged

    }

    override fun getQuality(): Int {
        launch {
            exportData.quality = dataStoreRepository.getQuality()
        }

        return exportData.quality
    }

    override fun getFormat(): String {
        launch { exportData.format = dataStoreRepository.getFormat() }
        return exportData.format
    }

    override fun getColors(count: Int): List<Int> {
        val colors = mutableListOf<Int>()
        val predefinedColors = listOf(
            Color.Red,
            Color.Blue,
            Color.Gray,
            Color.Magenta,
            Color.Black,
        )
        for (i in 0 until count) {
            colors.add(predefinedColors[i % predefinedColors.size].toArgb())
        }
        return colors
    }

    override fun setSelectedCurrency() {
        launch {
            data.currency = dataStoreRepository.getCurrency()
        }
    }

    override fun getTimeframe(): String {
        return data.timeframe
    }

    override fun saveBitmapToGallery(context: Context, bitmap: Bitmap) {
        val currentTimeMillis = System.currentTimeMillis()
        val date = Date(currentTimeMillis)
        val dateFormat = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
        val format = getFormat()
        val quality = getQuality()
        val mimeType: String
        val fileName: String
        if (format == "JPEG") {
            fileName = "${getTimeframe()}_graph_${dateFormat.format(date)}.jpg"
            mimeType = "image/jpeg"

        } else {
            fileName = "${getTimeframe()}_graph_${dateFormat.format(date)}.png"
            mimeType = "image/png"
        }

        val values = ContentValues().apply {
            put(MediaStore.Images.Media.TITLE, fileName)
            put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
            put(MediaStore.Images.Media.MIME_TYPE, mimeType)
            put(MediaStore.Images.Media.DATE_ADDED, currentTimeMillis / 1000)
            put(MediaStore.Images.Media.DATE_TAKEN, currentTimeMillis)
            put(
                MediaStore.Images.Media.DATA,
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).path + File.separator + fileName
            )
        }

        val contentResolver = context.contentResolver
        val uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        uri?.let {
            val outputStream: OutputStream? = contentResolver.openOutputStream(it)
            outputStream?.let { stream ->
                stream.use { stream ->
                    bitmap.compress(
                        if (format == "PNG") Bitmap.CompressFormat.PNG else Bitmap.CompressFormat.JPEG,
                        quality,
                        stream
                    )
                    stream.flush()
                }
            }
        }

        // Notify the gallery about the new image
        val imagePath = values.getAsString(MediaStore.Images.Media.DATA)
        imagePath?.let { path ->
            MediaScannerConnection.scanFile(context, arrayOf(path), null, null)
        }
        statsScreenUIState.value = StatsScreenUIState.ImageSaved
    }
}