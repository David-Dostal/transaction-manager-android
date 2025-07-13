@file:Suppress("SpellCheckingInspection")

package cz.mendelu.pef.xdostal8.transactionmanager.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first

class DataStoreRepositoryImpl(private val context: Context) : IDataStoreRepository {

    override suspend fun setFirstRun() {
        val preferencesKey = booleanPreferencesKey(DataStoreConstants.FIRST_RUN)
        context.dataStore.edit { preferences ->
            preferences[preferencesKey] = false
        }
    }

    override suspend fun getFirstRun(): Boolean {
        return try {
            val preferencesKey = booleanPreferencesKey(DataStoreConstants.FIRST_RUN)
            val preferences = context.dataStore.data.first()
            if (!preferences.contains(preferencesKey)) true
            else preferences[preferencesKey]!!
        } catch (e: Exception) {
            e.printStackTrace()
            true
        }
    }

    private val languageKey = stringPreferencesKey(DataStoreConstants.LANGUAGE_KEY)
    private val currencyKey = stringPreferencesKey(DataStoreConstants.CURRENCY_KEY)

    override suspend fun setLanguage(language: String) {
        context.dataStore.edit { preferences ->
            preferences[languageKey] = language
        }
    }

    override suspend fun getLanguage(): String {
        return try {
            val preferences = context.dataStore.data.first()
            preferences[languageKey] ?: ""
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    override suspend fun setCurrency(currency: String) {
        context.dataStore.edit { preferences ->
            preferences[currencyKey] = currency
        }
    }

    override suspend fun getCurrency(): String {
        return try {
            val preferences = context.dataStore.data.first()
            preferences[currencyKey] ?: ""
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    private val formatKey = stringPreferencesKey(DataStoreConstants.FORMAT_KEY)
    private val qualityKey = intPreferencesKey(DataStoreConstants.QUALITY_KEY)


    override suspend fun setFormat(format: String) {
        context.dataStore.edit { preferences ->
            preferences[formatKey] = format
        }
    }

    override suspend fun getFormat(): String {
        return try {
            val preferences = context.dataStore.data.first()
            preferences[formatKey] ?: ""
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    override suspend fun setQuality(quality: Int) {
        context.dataStore.edit { preferences ->
            preferences[qualityKey] = quality
        }
    }

    override suspend fun getQuality(): Int {
        return try {
            val preferences = context.dataStore.data.first()
            preferences[qualityKey] ?: 0
        } catch (e: Exception) {
            e.printStackTrace()
            0
        }
    }
}