package com.geminiboy.finalprojectbinar.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SetDestinationPreferences @Inject constructor(private val dataStore: DataStore<Preferences>) {
    private val dateDepartureKey = stringPreferencesKey("dateDeparture")
    private val dateReturnKey = stringPreferencesKey("dateReturn")
    private val passengerKey = stringPreferencesKey("passenger")
    private val seatKey = stringPreferencesKey("seat")

    fun getDeparture(): Flow<String> = dataStore.data.map {
        it[dateDepartureKey] ?: ""
    }
    fun getReturn(): Flow<String> = dataStore.data.map {
        it[dateReturnKey] ?: ""
    }
    fun getPassenger(): Flow<String> = dataStore.data.map {
        it[passengerKey] ?: ""
    }
    fun getSeat(): Flow<String> = dataStore.data.map {
        it[seatKey] ?: ""
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

    suspend fun setPassengers(passenger: String){
        dataStore.edit {
            it[passengerKey] = passenger
        }
    }

    suspend fun setSeat(seat: String){
        dataStore.edit {
            it[seatKey] = seat
        }
    }
}