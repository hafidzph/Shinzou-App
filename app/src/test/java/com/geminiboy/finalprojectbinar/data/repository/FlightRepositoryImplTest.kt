package com.geminiboy.finalprojectbinar.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.geminiboy.finalprojectbinar.data.local.datastore.FlightPreferences
import com.geminiboy.finalprojectbinar.data.remote.service.FlightService
import com.geminiboy.finalprojectbinar.model.airport.AirportResponse
import com.geminiboy.finalprojectbinar.model.flight.DetailFlightResponse
import com.geminiboy.finalprojectbinar.model.flight.FlightResponse
import com.geminiboy.finalprojectbinar.model.flight.SearchFlightResponse
import com.geminiboy.finalprojectbinar.model.flight.TransactionByIdResponse
import com.geminiboy.finalprojectbinar.model.historytransaction.TransactionResponse
import com.geminiboy.finalprojectbinar.wrapper.Resource
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FlightRepositoryImplTest{
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var api: FlightService

    @Mock
    private lateinit var flightPreferences: FlightPreferences

    private lateinit var flightRepository: FlightRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        flightRepository = FlightRepositoryImpl(api, flightPreferences)
    }

    @Test
    fun getFlight() = runBlocking {
        val response = mockk<FlightResponse>()
        Mockito.`when`(api.getFlight()).thenReturn(response)

        val result = flightRepository.getFlight()

        assert(result is Resource.Success)
        assertEquals((result as Resource.Success).data, response)
    }

    @Test
    fun getTransaction() = runBlocking {
        val response = mockk<TransactionResponse>()
        val token = "test"
        Mockito.`when`(api.getTransaction(token)).thenReturn(response)

        val result = flightRepository.getTransaction(token)

        assert(result is Resource.Success)
        assertEquals((result as Resource.Success).data, response)
    }

    @Test
    fun getAirport() = runBlocking {
        val response = mockk<AirportResponse>()
        Mockito.`when`(api.getAirport()).thenReturn(response)

        val result = flightRepository.getAirport()

        assert(result is Resource.Success)
        assertEquals((result as Resource.Success).data, response)
    }

    @Test
    fun getFlightById() = runBlocking {
        val response = mockk<DetailFlightResponse>()
        val id = "12421241235afe"
        Mockito.`when`(api.getFlightById(id)).thenReturn(response)

        val result = flightRepository.getFlightById(id)

        assert(result is Resource.Success)
        assertEquals((result as Resource.Success).data, response)
    }

    @Test
    fun getTransactionById() = runBlocking {
        val response = mockk<TransactionByIdResponse>()
        val token = "token"
        val id = "123"
        Mockito.`when`(api.getTransactionById(token, id)).thenReturn(response)

        val result = flightRepository.getTransactionById(token, id)

        assert(result is Resource.Success)
        assertEquals((result as Resource.Success).data, response)
    }

    @Test
    fun getSearchFlight() = runBlocking {
        val response = mockk<SearchFlightResponse>()
        val locationFrom = "Location A"
        val locationTo = "Location B"
        val departureDate = "2023-07-07"
        val passengers = 2
        val seatClass = "Economy"
        Mockito.`when`(api.searchFlight(locationFrom, locationTo, departureDate, passengers, seatClass)).thenReturn(response)

        val result = flightRepository.getSearchFlight(locationFrom, locationTo, departureDate, passengers, seatClass)

        assert(result is Resource.Success)
        assertEquals((result as Resource.Success).data, response)
    }
}