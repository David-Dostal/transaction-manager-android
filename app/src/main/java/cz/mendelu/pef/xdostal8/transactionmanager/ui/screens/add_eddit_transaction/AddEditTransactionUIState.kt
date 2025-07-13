package cz.mendelu.pef.xdostal8.transactionmanager.ui.screens.add_eddit_transaction

sealed class AddEditTransactionUIState {
    object Loading : AddEditTransactionUIState()
    object Default : AddEditTransactionUIState()
    object TransactionSaved : AddEditTransactionUIState()
    object TransactionChanged : AddEditTransactionUIState()
    object TransactionDeleted : AddEditTransactionUIState()
}
