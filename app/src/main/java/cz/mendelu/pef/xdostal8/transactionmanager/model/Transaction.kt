package cz.mendelu.pef.xdostal8.transactionmanager.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class Transaction(
    @ColumnInfo(name = "text") var text: String,

    @ColumnInfo(name = "amount") var amount: Double,

    @ColumnInfo(name = "isIncome") var isIncome: Boolean,

    @ColumnInfo(name = "date") var date: Long,

    @ColumnInfo(name = "category") var category: String,

    @ColumnInfo(name = "image") var image: String,


    ) {


    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    var id: Long? = null


}
