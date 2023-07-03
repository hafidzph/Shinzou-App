package com.geminiboy.finalprojectbinar.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FlightPreferences @Inject constructor(private val dataStore: DataStore<Preferences>) {
    private val departureFlightId = stringPreferencesKey("DEPARTURE_FLIGHT_ID")
    private val returnFlightId = stringPreferencesKey("RETURN_FLIGHT_ID")
    private val ticketPrice = stringPreferencesKey("PRICE_TICKET")
    private val transactionId = stringPreferencesKey("TRANSACTION_ID")

    suspend fun setDepartureAndReturnId(departure: String, _return: String?) {
        dataStore.edit { preferences ->
            preferences[departureFlightId] = departure
            _return?.let { preferences[returnFlightId] = it }
        }
    }

    suspend fun setDepartureId(departure: String) {
        dataStore.edit { preferences ->
            preferences[departureFlightId] = departure
        }
    }

    suspend fun setPrice(price: String) {
        dataStore.edit { preferences ->
            preferences[ticketPrice] = price
        }
    }

    suspend fun setTransactionId(id: String){
        dataStore.edit { preferences ->
            preferences[transactionId] = id
        }
    }

    fun getDepartureId(): Flow<String> = dataStore.data.map { preferences ->
        preferences[departureFlightId] ?: ""
    }

    fun getReturnId(): Flow<String?> = dataStore.data.map { preferences ->
        preferences[returnFlightId]
    }

    fun getTicketPrice(): Flow<String?> = dataStore.data.map { preferences ->
        preferences[ticketPrice]
    }

    fun getTransactionId(): Flow<String> = dataStore.data.map { preferences ->
        preferences[transactionId] ?: ""
    }
}