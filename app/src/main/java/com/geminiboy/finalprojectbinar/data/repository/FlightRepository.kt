package com.geminiboy.finalprojectbinar.data.repository

import com.geminiboy.finalprojectbinar.data.remote.service.FlightService
import com.geminiboy.finalprojectbinar.model.airport.AirportResponse
import com.geminiboy.finalprojectbinar.model.flight.FlightResponse
import com.geminiboy.finalprojectbinar.wrapper.Resource
import javax.inject.Inject

interface FlightRepository {
    suspend fun getFlight(token: String): Resource<FlightResponse>
    suspend fun getAirport(token: String): Resource<AirportResponse>
}

class FlightRepositoryImpl @Inject constructor(private val api: FlightService): FlightRepository{
    override suspend fun getFlight(token: String): Resource<FlightResponse> {
        return try {
            val response = api.getFlight(token)
            Resource.Success(response)
        }catch (e: Exception){
            Resource.Error(e.message!!)
        }
    }

    override suspend fun getAirport(token: String): Resource<AirportResponse> {
        return try {
            val response = api.getAirport(token)
            Resource.Success(response)
        }catch (e: Exception){
            Resource.Error(e.message!!)
        }
    }

}