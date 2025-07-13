package cz.mendelu.pef.xdostal8.transactionmanager.ui.screens.settings

interface SettingsScreenActions {

    fun changeAppLanguage(language: String)

    fun changeAppCurrency(currency: String)

    fun onLanguageChange(language: String)

    fun onCurrencyChange(currency: String)

    fun saveChanges()
    fun loadLanguage()

    fun loadCurrency()


}