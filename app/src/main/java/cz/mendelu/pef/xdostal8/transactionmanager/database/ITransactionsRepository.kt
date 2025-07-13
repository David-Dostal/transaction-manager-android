package cz.mendelu.pef.xdostal8.transactionmanager.database

import cz.mendelu.pef.xdostal8.transactionmanager.model.Transaction
import kotlinx.coroutines.flow.Flow

interface ITransactionsRepository {
    fun getAll(): Flow<List<Transaction>>
    suspend fun insert(transaction: Transaction): Long

    suspend fun deleteTransactionById(id: Long): Int
    suspend fun getTransactionById(id: Long): Transaction

    fun getTransactionByDate(date: Long): Flow<List<Transaction>>

    fun getTransactionsWithinTimeframe(
        startTimestamp: Long, endTimestamp: Long
    ): Flow<List<Transaction>>

    fun getAllSpendingCategories(): Flow<List<String>>

    fun getAllCategories(): Flow<List<String>>

    fun getAllIncomeCategories(): Flow<List<String>>
    fun getTransactionByDay(day: Long): Flow<List<Transaction>>
    suspend fun update(transaction: Transaction)
}