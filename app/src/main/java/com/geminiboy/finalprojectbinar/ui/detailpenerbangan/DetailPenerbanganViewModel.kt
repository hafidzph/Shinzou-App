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
    private val _detailFlight = MutableLiveData<Resource<DetailFlightResponse>>()
    val detailFlight: LiveData<Resource<DetailFlightResponse>> get() = _detailFlight

    fun getFlightById(id: String) = viewModelScope.launch {
        _detailFlight.postValue(flightRepository.getFlightById(id))
    }

    fun getToken() = authRepository.getToken().asLiveData()

    fun setDepartureId(departure: String) = viewModelScope.launch { flightRepository.setDepartureId(departure) }
    fun setTicketPrice(price: String) = viewModelScope.launch { flightRepository.setTicketPrice(price) }
}