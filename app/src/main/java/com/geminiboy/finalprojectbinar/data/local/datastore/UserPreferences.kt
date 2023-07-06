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
    private val setIdUser = stringPreferencesKey("setIdUser")
    private val nameUser = stringPreferencesKey("nameUser")
    private val phoneUser = stringPreferencesKey("phoneuser")
    private val emailUser = stringPreferencesKey("emailUser")

    suspend fun setToken(token: String){
        dataStore.edit {
            it[setToken] = token
        }
    }

    suspend fun setIdUser(id: String){
        dataStore.edit {
            it[setIdUser] = id
        }
    }

    suspend fun emailUser(email: String){
        dataStore.edit {
            it[emailUser] = email
        }
    }

    suspend fun setNameUser(name: String){
        dataStore.edit {
            it[nameUser] = name
        }
    }suspend fun setPhoneUser(phone: String){
        dataStore.edit {
            it[phoneUser] = phone
        }
    }

    fun getToken(): Flow<String> = dataStore.data.map {
        it[setToken] ?: ""
    }

    fun getId(): Flow<String> = dataStore.data.map {
        it[setIdUser] ?: ""
    }

    fun getName(): Flow<String> = dataStore.data.map {
        it[nameUser] ?: ""
    }

    fun getEmail(): Flow<String> = dataStore.data.map {
        it[emailUser] ?: ""
    }

    fun getPhone(): Flow<String> = dataStore.data.map {
        it[phoneUser] ?: ""
    }

    suspend fun clearToken(){
        dataStore.edit {
            it.remove(setToken)
        }
    }
}