package cz.mendelu.pef.xdostal8.transactionmanager.di

import cz.mendelu.pef.xdostal8.transactionmanager.ui.activities.viewmodels.AppIntroViewModel
import cz.mendelu.pef.xdostal8.transactionmanager.ui.activities.viewmodels.MainActivityViewModel
import cz.mendelu.pef.xdostal8.transactionmanager.ui.activities.viewmodels.SplashScreenActivityViewModel
import cz.mendelu.pef.xdostal8.transactionmanager.ui.screens.add_eddit_transaction.AddEditTransactionViewModel
import cz.mendelu.pef.xdostal8.transactionmanager.ui.screens.export.ExportScreenViewModel
import cz.mendelu.pef.xdostal8.transactionmanager.ui.screens.manage_currencies.ManageCurrenciesViewModel
import cz.mendelu.pef.xdostal8.transactionmanager.ui.screens.settings.SettingsScreenViewModel
import cz.mendelu.pef.xdostal8.transactionmanager.ui.screens.stats.StatsScreenViewModel
import cz.mendelu.pef.xdostal8.transactionmanager.ui.screens.transaction_list.TransactionListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    //for screens
    viewModel { TransactionListViewModel(get(), get()) }
    viewModel { AddEditTransactionViewModel(get()) }
    viewModel { SettingsScreenViewModel(get()) }
    viewModel { StatsScreenViewModel(get(), get()) }
    viewModel { ExportScreenViewModel(get()) }
    viewModel { ManageCurrenciesViewModel(get()) }
    // for activities
    viewModel { SplashScreenActivityViewModel(get()) }
    viewModel { MainActivityViewModel(get()) }
    viewModel { AppIntroViewModel(get()) }

}