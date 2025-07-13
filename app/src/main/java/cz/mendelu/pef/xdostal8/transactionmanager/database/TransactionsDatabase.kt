package cz.mendelu.pef.xdostal8.transactionmanager.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import cz.mendelu.pef.xdostal8.transactionmanager.model.Transaction

@Database(entities = [Transaction::class], version = 10, exportSchema = false)
abstract class TransactionsDatabase : RoomDatabase() {

    abstract fun transactionsDao(): TransactionsDao

    companion object {
        private var INSTANCE: TransactionsDatabase? = null
        fun getDatabase(context: Context): TransactionsDatabase {
            if (INSTANCE == null) {
                synchronized(TransactionsDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            TransactionsDatabase::class.java,
                            "transactions_database"
                        ).fallbackToDestructiveMigration()

                            .build()
                    }
                }
            }
            return INSTANCE!!
        }
    }
}