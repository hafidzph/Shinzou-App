package com.geminiboy.finalprojectbinar.ui.bottomsheet.searchingdestination

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.geminiboy.finalprojectbinar.data.repository.AuthRepository
import com.geminiboy.finalprojectbinar.data.repository.FlightRepository
import com.geminiboy.finalprojectbinar.model.airport.AirportResponse
import com.geminiboy.finalprojectbinar.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchingDestinationViewModel @Inject constructor(private val flightRepository: FlightRepository,
                                                        private val authRepository: AuthRepository): ViewModel() {
    private val _airport = MutableLiveData<Resource<AirportResponse>>()
    val airport: LiveData<Resource<AirportResponse>> get() = _airport

    fun getToken() = authRepository.getToken().asLiveData()

    fun getAirport(token: String) {
        _airport.postValue(Resource.Loading())
        viewModelScope.launch {
            _airport.postValue(flightRepository.getAirport("Bearer $token"))
        }
    }
}