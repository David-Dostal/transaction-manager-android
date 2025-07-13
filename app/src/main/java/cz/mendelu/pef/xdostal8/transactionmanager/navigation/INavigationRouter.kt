package cz.mendelu.pef.xdostal8.transactionmanager.navigation

import androidx.navigation.NavController

interface INavigationRouter {
    fun navigateBack()
    fun navigateToAddEditTransactionScreen(id: Long?)
    fun navigateToSettingsScreen()
    fun navigateToTransactionListScreen()
    fun getNavController(): NavController
    fun navigateToStatsScreen()
    fun navigateToManageCurrenciesScreen()

    fun navigateToExport()

}