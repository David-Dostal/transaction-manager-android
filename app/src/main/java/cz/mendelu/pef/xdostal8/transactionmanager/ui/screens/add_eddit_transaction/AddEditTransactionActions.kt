package cz.mendelu.pef.xdostal8.transactionmanager.ui.screens.add_eddit_transaction

import android.net.Uri

interface AddEditTransactionActions {
    fun saveTransaction()
    fun deleteTransaction()
    fun onTextChange(text: String)
    fun onCategoryChange(category: String)
    fun onAmountChange(amount: String?)
    fun onDateChange(date: Long)
    fun onIsIncomeChange(isIncome: Boolean)
    fun onImageChanged(image: Uri?)
}