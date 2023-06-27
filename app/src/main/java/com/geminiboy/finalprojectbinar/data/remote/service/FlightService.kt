package com.geminiboy.finalprojectbinar.data.remote.service

import com.geminiboy.finalprojectbinar.model.airport.AirportResponse
import com.geminiboy.finalprojectbinar.model.flight.FlightResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface FlightService {
    @GET("flight")
    suspend fun getFlight(@Header("Authorization") token: String): FlightResponse

    @GET("airport")
    suspend fun getAirport(@Header("Authorization") token: String): AirportResponse
}