package cz.mendelu.pef.xdostal8.transactionmanager.ui.activities.viewmodels

import cz.mendelu.pef.xdostal8.transactionmanager.ui.activities.states.SplashScreenUiState
import cz.mendelu.pef.xdostal8.transactionmanager.architecture.BaseViewModel
import cz.mendelu.pef.xdostal8.transactionmanager.datastore.IDataStoreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SplashScreenActivityViewModel(
    private val dataStoreRepository: IDataStoreRepository
) : BaseViewModel() {

    private val _splashScreenState =
        MutableStateFlow<SplashScreenUiState>(SplashScreenUiState.Default)
    val splashScreenState: StateFlow<SplashScreenUiState> = _splashScreenState

    fun checkAppState() {
        launch {
            if (dataStoreRepository.getFirstRun()) {
                if (dataStoreRepository.getLanguage().isEmpty()) {
                    dataStoreRepository.setLanguage("us")
                }
                if (dataStoreRepository.getCurrency().isEmpty()) {
                    dataStoreRepository.setCurrency("Kč")
                }
                _splashScreenState.value = SplashScreenUiState.RunForAFirstTime
            } else {
                _splashScreenState.value = SplashScreenUiState.ContinueToApp
            }

        }
    }
}