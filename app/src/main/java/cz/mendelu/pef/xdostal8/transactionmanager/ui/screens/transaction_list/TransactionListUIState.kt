package cz.mendelu.pef.xdostal8.transactionmanager.ui.screens.transaction_list

import cz.mendelu.pef.xdostal8.transactionmanager.model.Transaction

sealed class TransactionListUIState {
    object StatsScreenSelected : TransactionListUIState()
    object DateChanged : TransactionListUIState()
    object Default : TransactionListUIState()
    object Loading : TransactionListUIState()

    class Success(val transactions: List<Transaction>) : TransactionListUIState()
}