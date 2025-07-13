package cz.mendelu.pef.xdostal8.transactionmanager.navigation

import androidx.navigation.NavController

class NavigationRouterImpl(
    private val navController: NavController
) : INavigationRouter {

    override fun navigateBack() {
        navController.popBackStack()
    }

    override fun navigateToExport() {
        navController.navigate(Destination.ExportScreen.route)
    }

    override fun navigateToAddEditTransactionScreen(id: Long?) {
        navController.navigate(
            Destination.AddEditTransactionScreen.route + "/" + id
        )
    }

    override fun navigateToSettingsScreen() {
        navController.navigate(Destination.SettingsScreen.route)
    }

    override fun navigateToTransactionListScreen() {
        navController.navigate(Destination.TransactionListScreen.route)
    }

    override fun getNavController(): NavController = navController

    override fun navigateToStatsScreen() {
        navController.navigate(Destination.StatsScreen.route)

    }

    override fun navigateToManageCurrenciesScreen() {
        navController.navigate(Destination.ManageCurrenciesScreen.route)

    }


}