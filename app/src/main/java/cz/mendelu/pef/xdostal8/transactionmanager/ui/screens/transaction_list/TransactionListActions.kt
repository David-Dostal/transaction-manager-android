package cz.mendelu.pef.xdostal8.transactionmanager.ui.screens.transaction_list

interface TransactionListActions {

    fun changeSelectedDate(date: Long)
    fun loadTransactions()

    fun checkStartingDate(date: Long)

    fun setSelectedCurrency()
    fun setTodayDate()
    fun reselectScreen(screen: Int)

}