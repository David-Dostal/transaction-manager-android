package cz.mendelu.pef.xdostal8.transactionmanager.ui.screens.add_eddit_transaction

import cz.mendelu.pef.xdostal8.transactionmanager.model.Transaction
import java.util.*

class AddEditScreenData {

    var transaction: Transaction = Transaction(
        text = "",
        amount = 0.0,
        isIncome = false,
        date = Calendar.getInstance().timeInMillis,
        category = "",
        image = "None"
    )

    var rawAmount: String = ""
    var loading: Boolean = true
    var transactionTextError: Int? = null
    var transactionCategoryError: Int? = null
    var transactionAmountError: Int? = null

}
