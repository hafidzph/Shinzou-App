package com.geminiboy.finalprojectbinar.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesOf
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
    private val fromCity = stringPreferencesKey("fromCity")
    private val toCity = stringPreferencesKey("toCity")
    private val fromCityCode = stringPreferencesKey("fromCityCode")
    private val toCityCode = stringPreferencesKey("toCityCode")

    fun getPassenger(): Flow<Int> = dataStore.data.map { preferences ->
        val babyPassenger = preferences[babyPassengerKey]?.toIntOrNull() ?: 0
        val childrenPassenger = preferences[childrenPassengerKey]?.toIntOrNull() ?: 0
        val adultPassenger = preferences[adultPassengerKey]?.toIntOrNull() ?: 0
        babyPassenger + childrenPassenger + adultPassenger
    }

    fun getPassengerAdultChild(): Flow<Int> = dataStore.data.map { preferences ->
        val childrenPassenger = preferences[childrenPassengerKey]?.toIntOrNull() ?: 0
        val adultPassenger = preferences[adultPassengerKey]?.toIntOrNull() ?: 0
        childrenPassenger + adultPassenger
    }

    fun getFromJoin(): Flow<String> = dataStore.data.map {
        "${getFrom().first()} (${getFromCityCode().first()})"
    }

    fun getToJoin(): Flow<String> = dataStore.data.map {
        "${getTo().first()} (${getToCityCode().first()})"
    }

    fun getCodeCityJoin(): Flow<String> = dataStore.data.map {
        "${getFromCityCode().first()} > ${getToCityCode().first()}"
    }

    fun getPassengerJoinSeat(): Flow<String> = dataStore.data.map {
        "- ${getPassenger().first()} Penumpang - ${getSeat().first()}"
    }

    fun getBabyPassenger(): Flow<String> = dataStore.data.map { preferences ->
        preferences[babyPassengerKey] ?: ""
    }

    fun getChildrenPassenger(): Flow<String> = dataStore.data.map { preferences ->
        preferences[childrenPassengerKey] ?: ""
    }

    fun getAdultPassenger(): Flow<String> = dataStore.data.map { preferences ->
        preferences[adultPassengerKey] ?: ""
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

    fun getFrom(): Flow<String> = dataStore.data.map { preferences ->
        preferences[fromCity] ?: ""
    }

    fun getTo(): Flow<String> = dataStore.data.map { preferences ->
        preferences[toCity] ?: ""
    }

    fun getFromCityCode(): Flow<String> = dataStore.data.map { preferences ->
        preferences[fromCityCode] ?: ""
    }

    fun getToCityCode(): Flow<String> = dataStore.data.map { preferences ->
        preferences[toCityCode] ?: ""
    }

    suspend fun setFromCity(city: String, code: String) {
        dataStore.edit { preferences ->
            preferences[fromCity] = city
            preferences[fromCityCode] = code
        }
    }

    suspend fun setToCity(city: String, code: String) {
        dataStore.edit { preferences ->
            preferences[toCity] = city
            preferences[toCityCode] = code
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