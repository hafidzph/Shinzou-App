package com.geminiboy.finalprojectbinar.data.repository

import com.geminiboy.finalprojectbinar.data.local.datastore.FlightPreferences
import com.geminiboy.finalprojectbinar.data.remote.service.FlightService
import com.geminiboy.finalprojectbinar.model.airport.AirportResponse
import com.geminiboy.finalprojectbinar.model.flight.DetailFlightResponse
import com.geminiboy.finalprojectbinar.model.flight.FlightResponse
import com.geminiboy.finalprojectbinar.model.flight.SearchFlightResponse
import com.geminiboy.finalprojectbinar.model.flight.TransactionBody
import com.geminiboy.finalprojectbinar.model.flight.TransactionByIdResponse
import com.geminiboy.finalprojectbinar.model.flight.TransactionPostResponse
import com.geminiboy.finalprojectbinar.model.payment.PaymentBody
import com.geminiboy.finalprojectbinar.model.payment.PaymentResponse
import com.geminiboy.finalprojectbinar.wrapper.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface FlightRepository {
    suspend fun getFlight(): Resource<FlightResponse>
    suspend fun getAirport(): Resource<AirportResponse>
    suspend fun getSearchFlight(locationFrom: String,
                                locationTo: String,
                                departureDate: String,
                                passengers: Int,
                                seatClass: String) : Resource<SearchFlightResponse>
    suspend fun getFlightById(id: String): Resource<DetailFlightResponse>
    suspend fun postTransaction(token: String, transactionBody: TransactionBody): Resource<TransactionPostResponse>
    suspend fun getTransactionById(token: String, id: String): Resource<TransactionByIdResponse>
    suspend fun addPayment(token: String, booking_code: String, paymentBody: PaymentBody): Resource<PaymentResponse>
    suspend fun setDepartureId(departure: String)
    suspend fun setReturnId(_return: String)
    suspend fun setTicketPrice(price: String)
    suspend fun setDeparturePrice(price: Int)
    suspend fun setReturnPrice(price: Int)
    suspend fun setTransactionId(id: String)
    fun getDeparture(): Flow<String>
    fun getTransactionId(): Flow<String>
    fun getReturn(): Flow<String>
    fun getTicketPrice(): Flow<String>
    fun getTotalPriceRoundTrip(): Flow<Int>
    fun getPriceDeparture(): Flow<Int>
    fun getPriceReturn(): Flow<Int>
}

class FlightRepositoryImpl @Inject constructor(private val api: FlightService,
                                               private val preferences: FlightPreferences): FlightRepository{
    override suspend fun getFlight(): Resource<FlightResponse> {
        return try {
            val response = api.getFlight()
            Resource.Success(response)
        }catch (e: Exception){
            Resource.Error(e.message!!)
        }
    }

    override suspend fun getAirport(): Resource<AirportResponse> {
        return try {
            val response = api.getAirport()
            Resource.Success(response)
        }catch (e: Exception){
            Resource.Error(e.message!!)
        }
    }

    override suspend fun getSearchFlight(
        locationFrom: String,
        locationTo: String,
        departureDate: String,
        passengers: Int,
        seatClass: String
    ): Resource<SearchFlightResponse> {
        return try {
            val response = api.searchFlight(locationFrom, locationTo, departureDate, passengers, seatClass)
            Resource.Success(response)
        }catch (e: Exception){
            Resource.Error(e.message!!)
        }
    }

    override suspend fun getFlightById(id: String): Resource<DetailFlightResponse> {
        return try {
            val response = api.getFlightById(id)
            Resource.Success(response)
        }catch (e: Exception){
            Resource.Error(e.message!!)
        }
    }

    override suspend fun postTransaction(token:String, transactionBody: TransactionBody): Resource<TransactionPostResponse> {
        return try {
            val response = api.postTransaction(token, transactionBody)
            Resource.Success(response)
        }catch (e: Exception){
            Resource.Error(e.message!!)
        }
    }

    override suspend fun getTransactionById(
        token: String,
        id: String
    ): Resource<TransactionByIdResponse> {
        return try {
            val response = api.getTransactionById(token, id)
            Resource.Success(response)
        }catch (e: Exception){
            Resource.Error(e.message!!)
        }
    }

    override suspend fun addPayment(
        token: String,
        booking_code: String,
        paymentBody: PaymentBody
    ): Resource<PaymentResponse> {
        return try {
            val response = api.addPayment(token, booking_code, paymentBody)
            Resource.Success(response)
        }catch (e: Exception){
            Resource.Error(e.message!!)
        }
    }

    override suspend fun setDepartureId(departure: String) {
        preferences.setDepartureId(departure)
    }

    override suspend fun setReturnId(_return: String) {
        preferences.setReturnId(_return)
    }

    override suspend fun setTicketPrice(price: String) {
        preferences.setPrice(price)
    }

    override suspend fun setDeparturePrice(price: Int) {
        preferences.setPriceDeparture(price)
    }

    override suspend fun setReturnPrice(price: Int) {
        preferences.setPriceReturn(price)
    }

    override suspend fun setTransactionId(id: String) {
        preferences.setTransactionId(id)
    }

    override fun getDeparture(): Flow<String> {
        return preferences.getDepartureId()
    }

    override fun getTransactionId(): Flow<String> {
        return preferences.getTransactionId()
    }

    override fun getReturn(): Flow<String> {
        return preferences.getReturnId()
    }

    override fun getTicketPrice(): Flow<String> {
        return preferences.getTicketPrice()
    }

    override fun getTotalPriceRoundTrip(): Flow<Int> {
        return preferences.getTotalPriceRoundTrip()
    }

    override fun getPriceDeparture(): Flow<Int> {
        return preferences.getPriceDeparture()
    }

    override fun getPriceReturn(): Flow<Int> {
        return preferences.getPriceReturn()
    }

}