package com.geminiboy.finalprojectbinar.ui.detailpenerbangan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.geminiboy.finalprojectbinar.data.local.datastore.SetDestinationPreferences
import com.geminiboy.finalprojectbinar.data.repository.AuthRepository
import com.geminiboy.finalprojectbinar.data.repository.FlightRepository
import com.geminiboy.finalprojectbinar.model.flight.DetailFlightResponse
import com.geminiboy.finalprojectbinar.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailPenerbanganViewModel @Inject constructor(private val flightRepository: FlightRepository,
                                                     private val authRepository: AuthRepository,
                                                     val preferences: SetDestinationPreferences) : ViewModel(){
    private val _detailFlightDeparture = MutableLiveData<Resource<DetailFlightResponse>>()
    val detailFlightDeparture: LiveData<Resource<DetailFlightResponse>> get() = _detailFlightDeparture

    private val _detailFlightReturn = MutableLiveData<Resource<DetailFlightResponse>>()
    val detailFlightReturn: LiveData<Resource<DetailFlightResponse>> get() = _detailFlightReturn

    fun getTotalPriceRoundTrip() = flightRepository.getTotalPriceRoundTrip().asLiveData()

    fun getFlightByIdDeparture(id: String) = viewModelScope.launch {
        _detailFlightDeparture.postValue(flightRepository.getFlightById(id))
    }

    fun getFlightByIdReturn(id: String) = viewModelScope.launch {
        _detailFlightReturn.postValue(flightRepository.getFlightById(id))
    }

    fun getDepartureId(): LiveData<String> = flightRepository.getDeparture().asLiveData()
    fun getReturnId(): LiveData<String> = flightRepository.getReturn().asLiveData()
    fun getToken() = authRepository.getToken().asLiveData()

    fun setTicketPrice(price: String) = viewModelScope.launch { flightRepository.setTicketPrice(price) }
}