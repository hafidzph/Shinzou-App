package com.geminiboy.finalprojectbinar.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPreferences @Inject constructor(private val dataStore: DataStore<Preferences>) {
    private val setToken = stringPreferencesKey("setToken")

    suspend fun setToken(token: String){
        dataStore.edit {
            it[setToken] = token
        }
    }

    fun getToken(): Flow<String> = dataStore.data.map {
        it[setToken] ?: ""
    }

    suspend fun clearToken(){
        dataStore.edit {
            it.remove(setToken)
        }
    }
}