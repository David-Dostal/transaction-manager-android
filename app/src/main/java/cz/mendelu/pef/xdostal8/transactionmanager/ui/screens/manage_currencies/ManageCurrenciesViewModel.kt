package cz.mendelu.pef.xdostal8.transactionmanager.ui.screens.manage_currencies

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cz.mendelu.pef.xdostal8.transactionmanager.R
import cz.mendelu.pef.xdostal8.transactionmanager.architecture.BaseViewModel
import cz.mendelu.pef.xdostal8.transactionmanager.datastore.IDataStoreRepository
import kotlinx.coroutines.launch


class ManageCurrenciesViewModel(private val dataStoreRepository: IDataStoreRepository) :
    BaseViewModel(), ManageCurrenciesActions {

    var data: ManageCurrenciesScreenData = ManageCurrenciesScreenData()

    val manageCurrenciesScreenUIState: MutableState<ManageCurrenciesScreenUIState> =
        mutableStateOf(ManageCurrenciesScreenUIState.Default)


    override fun onCurrencyChange(currency: String) {
        data.currency = currency
        manageCurrenciesScreenUIState.value = ManageCurrenciesScreenUIState.CurrencyChanged()
    }

    override fun saveChanges() {
        if (data.currency == "") {
            data.currencyError = R.string.cannot_be_empty
            manageCurrenciesScreenUIState.value = ManageCurrenciesScreenUIState.CurrencyChanged()
        } else {
            launch {
                dataStoreRepository.setCurrency(data.currency)
            }
            manageCurrenciesScreenUIState.value = ManageCurrenciesScreenUIState.Saved
        }
    }


}


