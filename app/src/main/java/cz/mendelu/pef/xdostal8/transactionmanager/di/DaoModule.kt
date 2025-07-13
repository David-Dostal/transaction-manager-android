package cz.mendelu.pef.xdostal8.transactionmanager.di

import cz.mendelu.pef.xdostal8.transactionmanager.database.TransactionsDao
import cz.mendelu.pef.xdostal8.transactionmanager.database.TransactionsDatabase
import org.koin.dsl.module

val daoModule = module {

    fun provideDaoModule(database: TransactionsDatabase): TransactionsDao {
        return database.transactionsDao()
    }

    single { provideDaoModule(get()) }

}