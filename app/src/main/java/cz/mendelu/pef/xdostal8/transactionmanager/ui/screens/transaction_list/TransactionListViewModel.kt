package cz.mendelu.pef.xdostal8.transactionmanager.ui.screens.transaction_list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cz.mendelu.pef.xdostal8.transactionmanager.architecture.BaseViewModel
import cz.mendelu.pef.xdostal8.transactionmanager.database.ITransactionsRepository
import cz.mendelu.pef.xdostal8.transactionmanager.datastore.IDataStoreRepository
import cz.mendelu.pef.xdostal8.transactionmanager.model.Transaction
import kotlinx.coroutines.launch
import java.util.*

class TransactionListViewModel(
    private val repository: ITransactionsRepository,
    private val dataStoreRepository: IDataStoreRepository
) : BaseViewModel(), TransactionListActions {

    var data: TransactionListScreenData = TransactionListScreenData()

    val transactions = mutableStateOf(listOf<Transaction>())


    val transactionListUIState: MutableState<TransactionListUIState> =
        mutableStateOf(TransactionListUIState.Loading)


    override fun checkStartingDate(date: Long) {
        if (data.date == null) {
            data.date = date
        }
    }

    override fun loadTransactions() {
        setTodayDate()

        launch {
            data.date?.let { date ->
                repository.getTransactionByDay(date).collect {
                    transactions.value = it // Update the state here
                    transactionListUIState.value = TransactionListUIState.Success(it)
                }
            }
        }
    }

    override fun setSelectedCurrency() {
        launch {
            data.currency = dataStoreRepository.getCurrency()
        }
    }

    override fun setTodayDate() {
        launch {
            if (data.date == null) {
                data.date = Calendar.getInstance().timeInMillis
            }
        }
    }

    override fun reselectScreen(screen: Int) {
        data.selectedScreen = screen
        if (screen == 1) {
            transactionListUIState.value = TransactionListUIState.StatsScreenSelected
        }
    }


    override fun changeSelectedDate(date: Long) {
        launch {
            data.date = date
            transactionListUIState.value = TransactionListUIState.Loading
            loadTransactions()
        }
    }
}