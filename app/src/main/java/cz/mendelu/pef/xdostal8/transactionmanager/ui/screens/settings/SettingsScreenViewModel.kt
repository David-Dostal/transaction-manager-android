package cz.mendelu.pef.xdostal8.transactionmanager.ui.screens.settings

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cz.mendelu.pef.xdostal8.transactionmanager.architecture.BaseViewModel
import cz.mendelu.pef.xdostal8.transactionmanager.datastore.IDataStoreRepository
import kotlinx.coroutines.launch


class SettingsScreenViewModel(private val dataStoreRepository: IDataStoreRepository) :
    BaseViewModel(), SettingsScreenActions {

    var data: SettingsScreenData = SettingsScreenData()

    val settingsScreenUIState: MutableState<SettingsScreenUIState> =
        mutableStateOf(SettingsScreenUIState.Default)


    override fun changeAppLanguage(language: String) {
        launch {
            if (dataStoreRepository.getLanguage() != language) {
                dataStoreRepository.setLanguage(language)
            }
        }
    }

    override fun changeAppCurrency(currency: String) {
        launch {
            if (dataStoreRepository.getCurrency() != currency) {
                dataStoreRepository.setCurrency(currency)
            }
        }
    }

    override fun onLanguageChange(language: String) {
        data.language = language
        settingsScreenUIState.value = SettingsScreenUIState.SettingsChanged()
    }

    override fun onCurrencyChange(currency: String) {
        data.currency = currency
        settingsScreenUIState.value = SettingsScreenUIState.SettingsChanged()
    }

    override fun saveChanges() {
        launch {
            changeAppCurrency(data.currency)
            changeAppLanguage(data.language)
        }
        settingsScreenUIState.value = SettingsScreenUIState.Saved
    }


    override fun loadLanguage() {
        data.loading = true
        launch {
            data.language = dataStoreRepository.getLanguage()
        }
        loadCurrency()
    }

    override fun loadCurrency() {
        launch {
            data.currency = dataStoreRepository.getCurrency()
        }
        settingsScreenUIState.value = SettingsScreenUIState.Loaded

    }
}


