package cz.mendelu.pef.xdostal8.transactionmanager.ui.screens.export


sealed class ExportScreenUIState {
    object Loading : ExportScreenUIState()

    object Default : ExportScreenUIState()


    class ExportSettingsChanged() : ExportScreenUIState()


    object Loaded : ExportScreenUIState()

    object Saved : ExportScreenUIState()
}