package com.geminiboy.finalprojectbinar.data.remote.service

import com.geminiboy.finalprojectbinar.model.airport.AirportResponse
import com.geminiboy.finalprojectbinar.model.flight.FlightResponse
import com.geminiboy.finalprojectbinar.model.flight.SearchFlightOneTrip
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface FlightService {
    @GET("flight")
    suspend fun getFlight(): FlightResponse

    @GET("airport")
    suspend fun getAirport(): AirportResponse

    @GET("search-flight")
    suspend fun oneTripFlight(
        @Query("location_from") locationFrom: String,
        @Query("location_to") locationTo: String,
        @Query("departure_date") departureDate: String,
        @Query("passengers") passengers: Int,
        @Query("seat_class") seatClass: String
    ): SearchFlightOneTrip

    @GET("search-flight")
    fun roundTripFlight(
        @Query("location_from") locationFrom: String,
        @Query("location_to") locationTo: String,
        @Query("departure_date") departureDate: String,
        @Query("return_date") returnDate: String,
        @Query("passengers") passengers: Int,
        @Query("seat_class") seatClass: String
    ): FlightResponse
}