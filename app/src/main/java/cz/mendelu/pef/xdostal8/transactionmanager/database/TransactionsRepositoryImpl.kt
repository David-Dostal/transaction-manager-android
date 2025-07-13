package cz.mendelu.pef.xdostal8.transactionmanager.database

import cz.mendelu.pef.xdostal8.transactionmanager.model.Transaction
import kotlinx.coroutines.flow.Flow

class TransactionsRepositoryImpl(private val transactionsDao: TransactionsDao) :
    ITransactionsRepository {

    override fun getAll(): Flow<List<Transaction>> {
        return transactionsDao.getAll()
    }

    override fun getTransactionByDate(date: Long): Flow<List<Transaction>> {
        return transactionsDao.getTransactionByDate(date)
    }

    override fun getTransactionsWithinTimeframe(
        startTimestamp: Long, endTimestamp: Long
    ): Flow<List<Transaction>> {
        return transactionsDao.getTransactionsWithinTimeframe(startTimestamp, endTimestamp)
    }

    override fun getAllSpendingCategories(): Flow<List<String>> {
        return transactionsDao.getAllSpendingCategories()
    }

    override fun getAllCategories(): Flow<List<String>> {
        return transactionsDao.getAllCategories()
    }

    override fun getAllIncomeCategories(): Flow<List<String>> {
        return transactionsDao.getAllIncomeCategories()
    }


    override fun getTransactionByDay(day: Long): Flow<List<Transaction>> {
        return transactionsDao.getTransactionByDay(day)
    }

    override suspend fun insert(transaction: Transaction): Long {
        return transactionsDao.insert(transaction)
    }

    override suspend fun deleteTransactionById(id: Long): Int {
        return transactionsDao.deleteTransactionById(id)
    }

    override suspend fun getTransactionById(id: Long): Transaction =
        transactionsDao.getTransactionById(id)

    override suspend fun update(transaction: Transaction) {
        transactionsDao.update(transaction)
    }


}