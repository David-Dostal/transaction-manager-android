package cz.mendelu.pef.xdostal8.transactionmanager.ui.screens.manage_currencies

interface ManageCurrenciesActions {


    fun onCurrencyChange(currency: String)

    fun saveChanges()
}