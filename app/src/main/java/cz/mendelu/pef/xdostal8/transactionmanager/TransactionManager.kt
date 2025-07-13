package cz.mendelu.pef.xdostal8.transactionmanager

import android.app.Application
import android.content.Context
import cz.mendelu.pef.xdostal8.transactionmanager.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TransactionManager : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        startKoin {
            androidContext(this@TransactionManager)
            modules(
                listOf(
                    viewModelModule,
                    repositoryModule,
                    daoModule,
                    databaseModule,
                    viewModelModule,
                    dataStoreModule
                )
            )
        }
    }

    companion object {
        lateinit var appContext: Context
            private set
    }

}