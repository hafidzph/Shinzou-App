package com.geminiboy.finalprojectbinar.data.repository

import com.geminiboy.finalprojectbinar.data.remote.service.FlightService
import com.geminiboy.finalprojectbinar.model.airport.AirportResponse
import com.geminiboy.finalprojectbinar.model.flight.FlightResponse
import com.geminiboy.finalprojectbinar.model.flight.SearchFlightOneTrip
import com.geminiboy.finalprojectbinar.wrapper.Resource
import javax.inject.Inject

interface FlightRepository {
    suspend fun getFlight(): Resource<FlightResponse>
    suspend fun getAirport(): Resource<AirportResponse>
    suspend fun getSearchFlight(locationFrom: String,
                                locationTo: String,
                                departureDate: String,
                                passengers: Int,
                                seatClass: String) : Resource<SearchFlightOneTrip>
}

class FlightRepositoryImpl @Inject constructor(private val api: FlightService): FlightRepository{
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
    ): Resource<SearchFlightOneTrip> {
        return try {
            val response = api.oneTripFlight(locationFrom, locationTo, departureDate, passengers, seatClass)
            Resource.Success(response)
        }catch (e: Exception){
            Resource.Error(e.message!!)
        }
    }

}