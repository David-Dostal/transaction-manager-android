package cz.mendelu.pef.xdostal8.transactionmanager.di

import cz.mendelu.pef.xdostal8.transactionmanager.TransactionManager
import cz.mendelu.pef.xdostal8.transactionmanager.database.TransactionsDatabase
import org.koin.dsl.module

val databaseModule = module {

    fun provideDatabase(): TransactionsDatabase =
        TransactionsDatabase.getDatabase(TransactionManager.appContext)

    single { provideDatabase() }

}