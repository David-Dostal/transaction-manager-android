package cz.mendelu.pef.xdostal8.transactionmanager.ui.screens.manage_currencies


sealed class ManageCurrenciesScreenUIState {
    object Default : ManageCurrenciesScreenUIState()


    object Saved : ManageCurrenciesScreenUIState()

    class CurrencyChanged() : ManageCurrenciesScreenUIState()

}