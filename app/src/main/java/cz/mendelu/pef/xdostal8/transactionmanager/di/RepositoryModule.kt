package cz.mendelu.pef.xdostal8.transactionmanager.di

import cz.mendelu.pef.xdostal8.transactionmanager.database.ITransactionsRepository
import cz.mendelu.pef.xdostal8.transactionmanager.database.TransactionsDao
import cz.mendelu.pef.xdostal8.transactionmanager.database.TransactionsRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {

    fun provideTransactionsRepository(dao: TransactionsDao): ITransactionsRepository {
        return TransactionsRepositoryImpl(dao)
    }

    single { provideTransactionsRepository(get()) }

}