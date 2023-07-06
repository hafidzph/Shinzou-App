package com.geminiboy.finalprojectbinar.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FlightPreferences @Inject constructor(private val dataStore: DataStore<Preferences>) {
    private val departureFlightId = stringPreferencesKey("DEPARTURE_FLIGHT_ID")
    private val returnFlightId = stringPreferencesKey("RETURN_FLIGHT_ID")
    private val ticketPrice = stringPreferencesKey("PRICE_TICKET")
    private val transactionId = stringPreferencesKey("TRANSACTION_ID")
    private val priceDeparture = intPreferencesKey("PRICE_DEPARTURE")
    private val priceReturn = intPreferencesKey("PRICE_RETURN")

    suspend fun setReturnId(_return: String) {
        dataStore.edit { preferences ->
            preferences[returnFlightId] = _return
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
    suspend fun setPriceDeparture(price: Int) {
        dataStore.edit { preferences ->
            preferences[priceDeparture] = price
        }
    }

    suspend fun setPriceReturn(price: Int) {
        dataStore.edit { preferences ->
            preferences[priceReturn] = price
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

    fun getReturnId(): Flow<String> = dataStore.data.map { preferences ->
        preferences[returnFlightId] ?: ""
    }

    fun getTicketPrice(): Flow<String> = dataStore.data.map { preferences ->
        preferences[ticketPrice] ?: ""
    }

    fun getTotalPriceRoundTrip(): Flow<Int> = dataStore.data.map { preferences ->
        val priceD = preferences[priceDeparture] ?: 0
        val priceR = preferences[priceReturn] ?: 0
        priceD + priceR
    }

    fun getPriceDeparture(): Flow<Int> = dataStore.data.map { preferences ->
        preferences[priceDeparture] ?: 0
    }

    fun getPriceReturn(): Flow<Int> = dataStore.data.map { preferences ->
        preferences[priceReturn] ?: 0
    }

    fun getTransactionId(): Flow<String> = dataStore.data.map { preferences ->
        preferences[transactionId] ?: ""
    }
}