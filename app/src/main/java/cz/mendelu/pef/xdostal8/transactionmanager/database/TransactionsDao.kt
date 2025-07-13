package cz.mendelu.pef.xdostal8.transactionmanager.database

import androidx.room.*
import cz.mendelu.pef.xdostal8.transactionmanager.model.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionsDao {

    @Query("SELECT * FROM transactions WHERE date >= :endTimestamp AND date <= :startTimestamp")
    fun getTransactionsWithinTimeframe(
        startTimestamp: Long, endTimestamp: Long
    ): Flow<List<Transaction>>

    @Query("SELECT * FROM transactions")
    fun getAll(): Flow<List<Transaction>>

    @Query("SELECT * FROM transactions WHERE date = :date")
    fun getTransactionByDate(date: Long): Flow<List<Transaction>>

    @Query("SELECT category FROM transactions WHERE isIncome = 0")
    fun getAllSpendingCategories(): Flow<List<String>>

    @Query("SELECT category FROM transactions WHERE isIncome = 1")
    fun getAllIncomeCategories(): Flow<List<String>>

    @Query("SELECT category FROM transactions")
    fun getAllCategories(): Flow<List<String>>


    @Query("SELECT * FROM transactions WHERE strftime('%Y-%m-%d', date / 1000, 'unixepoch') = strftime('%Y-%m-%d', :day / 1000, 'unixepoch')")
    fun getTransactionByDay(day: Long): Flow<List<Transaction>>

    @Query("DELETE FROM transactions WHERE id = :id")
    suspend fun deleteTransactionById(id: Long): Int


    @Insert
    suspend fun insert(transaction: Transaction): Long

    @Update
    suspend fun update(transaction: Transaction)


    @Query("SELECT * FROM transactions WHERE id = :id")
    suspend fun getTransactionById(id: Long): Transaction


}