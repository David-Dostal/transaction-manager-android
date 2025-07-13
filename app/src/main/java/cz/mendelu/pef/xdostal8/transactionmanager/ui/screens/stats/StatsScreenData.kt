package cz.mendelu.pef.xdostal8.transactionmanager.ui.screens.stats

import android.graphics.Bitmap
import java.util.*

class StatsScreenData {
    var date: Long = Calendar.getInstance().timeInMillis
    var timeframe: String = "week"
    var spendingsSelected: Boolean = true
    var categorySelected: String = ""
    var loading: Boolean = true
    var selectedScreen: Int = 1
    var graphImage: Bitmap? = null
    var currency: String? = null


}

class StatsScreenExportData {
    var quality: Int = 100
    var format: String = "PNG"
    var createImage: Boolean = false

}
