package com.geminiboy.finalprojectbinar.data.remote.service

import com.geminiboy.finalprojectbinar.model.airport.AirportResponse
import com.geminiboy.finalprojectbinar.model.flight.DetailFlightResponse
import com.geminiboy.finalprojectbinar.model.flight.FlightResponse
import com.geminiboy.finalprojectbinar.model.flight.SearchFlightResponse
import com.geminiboy.finalprojectbinar.model.flight.TransactionBody
import com.geminiboy.finalprojectbinar.model.flight.TransactionByIdResponse
import com.geminiboy.finalprojectbinar.model.flight.TransactionPostResponse
import com.geminiboy.finalprojectbinar.model.payment.PaymentBody
import com.geminiboy.finalprojectbinar.model.payment.PaymentResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface FlightService {
    @GET("flight")
    suspend fun getFlight(): FlightResponse

    @GET("flight/{id}")
    suspend fun getFlightById(@Path("id") id: String): DetailFlightResponse

    @GET("airport")
    suspend fun getAirport(): AirportResponse

    @GET("search-flight")
    suspend fun searchFlight(
        @Query("location_from") locationFrom: String,
        @Query("location_to") locationTo: String,
        @Query("departure_date") departureDate: String,
        @Query("passengers") passengers: Int,
        @Query("seat_class") seatClass: String
    ): SearchFlightResponse

    @POST("transaction")
    suspend fun postTransaction(@Header("Authorization") authorization: String, @Body transactionBody: TransactionBody): TransactionPostResponse
    @PUT("transaction/{booking_code}")
    suspend fun addPayment(@Header("Authorization") authorization: String, @Path("booking_code") booking_code: String, @Body paymentBody: PaymentBody): PaymentResponse

    @GET("transaction/{id}")
    suspend fun getTransactionById(@Header("Authorization") authorization: String, @Path("id") id: String): TransactionByIdResponse
}