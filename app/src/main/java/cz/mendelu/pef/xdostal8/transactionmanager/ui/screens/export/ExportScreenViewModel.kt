package cz.mendelu.pef.xdostal8.transactionmanager.ui.screens.export

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cz.mendelu.pef.xdostal8.transactionmanager.architecture.BaseViewModel
import cz.mendelu.pef.xdostal8.transactionmanager.datastore.IDataStoreRepository
import kotlinx.coroutines.launch


class ExportScreenViewModel(private val dataStoreRepository: IDataStoreRepository) :
    BaseViewModel(), ExportScreenActions {

    var data: ExportScreenData = ExportScreenData()

    val exportScreenUIState: MutableState<ExportScreenUIState> =
        mutableStateOf(ExportScreenUIState.Loading)

    override fun onFormatChange(format: String) {
        data.loading = true

        data.format = format
        exportScreenUIState.value = ExportScreenUIState.ExportSettingsChanged()
    }

    override fun onQualityChange(quality: Int) {
        data.loading = true
        data.quality = quality
        exportScreenUIState.value = ExportScreenUIState.ExportSettingsChanged()

    }

    override fun saveChanges() {
        launch {
            changeExportFormat(data.format)
            changeExportQuality(data.quality)
        }
        exportScreenUIState.value = ExportScreenUIState.Saved
    }

    override fun changeExportFormat(format: String) {
        launch {
            if (dataStoreRepository.getFormat() != format) {
                dataStoreRepository.setFormat(format)
            }
        }
    }

    override fun changeExportQuality(quality: Int) {
        launch {
            if (dataStoreRepository.getQuality() != quality) {
                dataStoreRepository.setQuality(quality)
            }
        }
    }


    override fun loadFormat() {
        data.loading = true
        launch {
            if (dataStoreRepository.getFormat().isEmpty()) {
                dataStoreRepository.setFormat("PNG")
            }
            data.format = dataStoreRepository.getFormat()
        }
        data.loading = false
        loadQuality()
    }

    override fun loadQuality() {
        data.loading = true
        launch {
            data.quality = dataStoreRepository.getQuality()
        }
        data.loading = false

        exportScreenUIState.value = ExportScreenUIState.Loaded

    }
}