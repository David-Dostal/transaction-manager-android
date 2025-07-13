package cz.mendelu.pef.xdostal8.transactionmanager.di

import android.content.Context
import cz.mendelu.pef.xdostal8.transactionmanager.datastore.DataStoreRepositoryImpl
import cz.mendelu.pef.xdostal8.transactionmanager.datastore.IDataStoreRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataStoreModule = module {
    single { provideDataStoreRepository(androidContext()) }
}

fun provideDataStoreRepository(context: Context): IDataStoreRepository =
    DataStoreRepositoryImpl(context)