package cz.mendelu.pef.xdostal8.transactionmanager.ui.activities.viewmodels

import cz.mendelu.pef.xdostal8.transactionmanager.architecture.BaseViewModel
import cz.mendelu.pef.xdostal8.transactionmanager.datastore.IDataStoreRepository

class AppIntroViewModel(private val dataStoreRepository: IDataStoreRepository) : BaseViewModel() {

    suspend fun setFirstRun() {
        dataStoreRepository.setFirstRun()
    }
}