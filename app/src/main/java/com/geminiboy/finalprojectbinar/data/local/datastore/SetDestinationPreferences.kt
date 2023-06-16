package com.geminiboy.finalprojectbinar.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SetDestinationPreferences @Inject constructor(private val dataStore: DataStore<Preferences>) {
    private val dateDepartureKey = stringPreferencesKey("dateDeparture")
    private val dateReturnKey = stringPreferencesKey("dateReturn")
    private val babyPassengerKey = stringPreferencesKey("baby")
    private val childrenPassengerKey = stringPreferencesKey("children")
    private val adultPassengerKey = stringPreferencesKey("adult")
    private val seatKey = stringPreferencesKey("seatClass")

    fun getPassenger(): Flow<Int> = dataStore.data.map { preferences ->
        val babyPassenger = preferences[babyPassengerKey]?.toIntOrNull() ?: 0
        val childrenPassenger = preferences[childrenPassengerKey]?.toIntOrNull() ?: 0
        val adultPassenger = preferences[adultPassengerKey]?.toIntOrNull() ?: 0
        babyPassenger + childrenPassenger + adultPassenger
    }

    suspend fun getBabyPassenger(): String? {
        return dataStore.data.first()[babyPassengerKey]
    }

    suspend fun getChildrenPassenger(): String? {
        return dataStore.data.first()[childrenPassengerKey]
    }

    suspend fun getAdultPassenger(): String? {
        return dataStore.data.first()[adultPassengerKey]
    }

    fun getDateDeparture(): Flow<String> = dataStore.data.map { preferences ->
        preferences[dateDepartureKey] ?: ""
    }

    fun getDateReturn(): Flow<String> = dataStore.data.map { preferences ->
        preferences[dateReturnKey] ?: ""
    }

    fun getSeat(): Flow<String> = dataStore.data.map { preferences ->
        preferences[seatKey] ?: ""
    }

    suspend fun setDeparture(date: String){
        dataStore.edit {
            it[dateDepartureKey] = date
        }
    }

    suspend fun setReturn(date: String){
        dataStore.edit {
            it[dateReturnKey] = date
        }
    }

    suspend fun setBabyPassengers(passenger: String){
        dataStore.edit {
            it[babyPassengerKey] = passenger
        }
    }

    suspend fun setChildrenPassengers(passenger: String){
        dataStore.edit {
            it[childrenPassengerKey] = passenger
        }
    }

    suspend fun setAdultPassengers(passenger: String){
        dataStore.edit {
            it[adultPassengerKey] = passenger
        }
    }

    suspend fun setSeat(seat: String){
        dataStore.edit {
            it[seatKey] = seat
        }
    }

    suspend fun setDateDeparture(date: String){
        dataStore.edit {
            it[dateDepartureKey] = date
        }
    }

    suspend fun setReturnDeparture(date: String){
        dataStore.edit {
            it[dateReturnKey] = date
        }
    }

    suspend fun clearPreferences() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}