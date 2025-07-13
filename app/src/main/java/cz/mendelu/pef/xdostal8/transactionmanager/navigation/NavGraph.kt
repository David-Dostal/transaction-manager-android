package cz.mendelu.pef.xdostal8.transactionmanager.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import cz.mendelu.pef.xdostal8.transactionmanager.ui.screens.add_eddit_transaction.AddEditTransactionScreen
import cz.mendelu.pef.xdostal8.transactionmanager.ui.screens.export.ExportScreen
import cz.mendelu.pef.xdostal8.transactionmanager.ui.screens.manage_currencies.ManageCurrenciesScreen
import cz.mendelu.pef.xdostal8.transactionmanager.ui.screens.settings.SettingsScreen
import cz.mendelu.pef.xdostal8.transactionmanager.ui.screens.stats.StatsScreen
import cz.mendelu.pef.xdostal8.transactionmanager.ui.screens.transaction_list.TransactionListScreen

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    navigation: INavigationRouter = remember {
        NavigationRouterImpl(navController)
    },
    startDestination: String
) {
    NavHost(
        navController = navController, startDestination = startDestination
    ) {

        composable(Destination.TransactionListScreen.route) {
            TransactionListScreen(navigation)
        }

        composable(Destination.ExportScreen.route) {
            ExportScreen(navigation)
        }

        composable(Destination.StatsScreen.route) {
            StatsScreen(navigation)
        }

        composable(Destination.SettingsScreen.route) {
            SettingsScreen(navigation)
        }

        composable(Destination.ManageCurrenciesScreen.route) {
            ManageCurrenciesScreen(navigation)
        }




        composable(
            Destination.AddEditTransactionScreen.route + "/{id}",
            arguments = listOf(navArgument("id") {
                type = NavType.LongType
                defaultValue = -1L
            })
        ) {
            val id = it.arguments?.getLong("id")
            AddEditTransactionScreen(
                navigation = navigation, id = if (id != -1L) id else null
            )
        }


    }


}


