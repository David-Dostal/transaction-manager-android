package cz.mendelu.pef.xdostal8.transactionmanager.datastore

interface IDataStoreRepository {
    suspend fun setFirstRun()
    suspend fun getFirstRun(): Boolean

    suspend fun setLanguage(language: String)
    suspend fun getLanguage(): String
    suspend fun setCurrency(currency: String)
    suspend fun getCurrency(): String

    suspend fun setFormat(format: String)
    suspend fun getFormat(): String
    suspend fun setQuality(quality: Int)
    suspend fun getQuality(): Int
}