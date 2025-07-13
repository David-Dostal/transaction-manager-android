package cz.mendelu.pef.xdostal8.transactionmanager.navigation

sealed class Destination(val route: String) {
    object TransactionListScreen : Destination(route = "transaction_list")
    object AddEditTransactionScreen : Destination(route = "add_edit_transaction")
    object SettingsScreen : Destination(route = "settings")
    object StatsScreen : Destination(route = "stats")
    object ManageCurrenciesScreen : Destination(route = "manage_currencies")

    object ExportScreen : Destination(route = "export")

}
