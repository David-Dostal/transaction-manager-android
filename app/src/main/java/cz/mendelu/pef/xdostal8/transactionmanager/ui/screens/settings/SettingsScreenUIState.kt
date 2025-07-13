package cz.mendelu.pef.xdostal8.transactionmanager.ui.screens.settings



sealed class SettingsScreenUIState {
    object Default : SettingsScreenUIState()


    class SettingsChanged() : SettingsScreenUIState()


    object Loaded : SettingsScreenUIState()

    object Saved : SettingsScreenUIState()
}