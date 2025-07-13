package cz.mendelu.pef.xdostal8.transactionmanager.ui.screens.add_eddit_transaction

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cz.mendelu.pef.xdostal8.transactionmanager.R
import cz.mendelu.pef.xdostal8.transactionmanager.architecture.BaseViewModel
import cz.mendelu.pef.xdostal8.transactionmanager.database.ITransactionsRepository
import kotlinx.coroutines.launch

class AddEditTransactionViewModel(private val repository: ITransactionsRepository) :
    BaseViewModel(), AddEditTransactionActions {
    var data: AddEditScreenData = AddEditScreenData()
    var transactionId: Long? = null
    var addEditTransactionUIState: MutableState<AddEditTransactionUIState> =
        mutableStateOf(AddEditTransactionUIState.Loading)

    override fun saveTransaction() {
        if (data.transaction.text.isEmpty()) {
            data.transactionTextError = R.string.cannot_be_empty
            addEditTransactionUIState.value = AddEditTransactionUIState.TransactionChanged
        } else {
            data.loading = true
            launch {
                if (transactionId == null) {
                    val id = repository.insert(data.transaction)
                    if (id > 0) {
                        addEditTransactionUIState.value = AddEditTransactionUIState.TransactionSaved
                    }
                } else {
                    repository.update(data.transaction)
                    addEditTransactionUIState.value = AddEditTransactionUIState.TransactionSaved
                }
            }
        }
    }

    override fun onTextChange(text: String) {
        data.transaction.text = text
        addEditTransactionUIState.value = AddEditTransactionUIState.TransactionChanged
    }

    override fun onCategoryChange(category: String) {
        data.transaction.category = category
        addEditTransactionUIState.value = AddEditTransactionUIState.TransactionChanged
    }

    override fun onAmountChange(amount: String?) {

        if (amount != null) {
            data.rawAmount = amount
            if (amount != "") {
                data.transaction.amount = amount.toDouble()
            }
        } else {
            data.transaction.amount = 0.0
        }
        addEditTransactionUIState.value = AddEditTransactionUIState.TransactionChanged
    }

    override fun onDateChange(date: Long) {
        data.transaction.date = date
        addEditTransactionUIState.value = AddEditTransactionUIState.TransactionChanged
    }

    override fun onIsIncomeChange(isIncome: Boolean) {
        data.transaction.isIncome = !data.transaction.isIncome
        addEditTransactionUIState.value = AddEditTransactionUIState.TransactionChanged
    }

    override fun onImageChanged(image: Uri?) {
        if (image != null) {
            data.transaction.image = image.toString()
        } else {
            data.transaction.image = "None"
        }
        addEditTransactionUIState.value = AddEditTransactionUIState.TransactionChanged

    }

    fun initTransaction() {
        data.loading = true
        if (transactionId != null) {
            launch {
                data.transaction = repository.getTransactionById(transactionId!!)
                data.rawAmount = data.transaction.amount.toString()
                data.loading = false
                addEditTransactionUIState.value = AddEditTransactionUIState.TransactionChanged
            }
        } else {
            data.loading = false
            addEditTransactionUIState.value = AddEditTransactionUIState.TransactionChanged
        }
    }

    override fun deleteTransaction() {
        launch {
            repository.deleteTransactionById(transactionId!!)
            addEditTransactionUIState.value = AddEditTransactionUIState.TransactionDeleted
        }
    }
}