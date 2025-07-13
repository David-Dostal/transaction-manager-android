package cz.mendelu.pef.xdostal8.transactionmanager.ui.screens.stats

import android.content.Context
import android.graphics.Bitmap

interface StatsScreenActions {
    fun reselectScreen(screen: Int)
    fun loadTransactions()
    fun changeTimeframe(timeframe: String)
    fun transactionTypeSwitched()
    fun onCategoryChange(category: String)
    fun changeQuality(quality: Int)
    fun changeFormat(format: String)
    fun getQuality(): Int
    fun getFormat(): String
    fun loadCategories()
    fun saveImage(image: Bitmap)
    fun createImage()

    fun setSelectedCurrency()

    fun getTimeframe(): String
    fun getColors(count: Int): List<Int>

    fun saveBitmapToGallery(context: Context, bitmap: Bitmap)
}