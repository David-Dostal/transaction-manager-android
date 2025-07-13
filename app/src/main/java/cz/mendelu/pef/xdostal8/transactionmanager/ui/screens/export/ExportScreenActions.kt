package cz.mendelu.pef.xdostal8.transactionmanager.ui.screens.export

interface ExportScreenActions {

    fun onFormatChange(format: String)

    fun onQualityChange(quality: Int)

    fun saveChanges()

    fun changeExportFormat(format: String)

    fun changeExportQuality(quality: Int)


    fun loadFormat()

    fun loadQuality()


}