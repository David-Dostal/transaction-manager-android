package cz.mendelu.pef.xdostal8.transactionmanager.ui.screens.stats

import cz.mendelu.pef.xdostal8.transactionmanager.model.Transaction


sealed class StatsScreenUIState {

    object ManagerScreenSelected : StatsScreenUIState()

    object TimeframeChanged : StatsScreenUIState()
    object TransactionTypeChanged : StatsScreenUIState()
    object CategoryChanged : StatsScreenUIState()
    object Default : StatsScreenUIState()

    object ExportSettingsChanged : StatsScreenUIState()


    object ImageSaved : StatsScreenUIState()
    object CreateImage : StatsScreenUIState()


    class CategoriesLoaded(val categories: List<String>) : StatsScreenUIState()

    class Success(val transactions: List<Transaction>) : StatsScreenUIState()
}
