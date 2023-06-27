package com.geminiboy.finalprojectbinar.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.geminiboy.finalprojectbinar.data.local.datastore.SetDestinationPreferences
import com.geminiboy.finalprojectbinar.data.repository.AuthRepository
import com.geminiboy.finalprojectbinar.data.repository.FlightRepository
import com.geminiboy.finalprojectbinar.model.flight.FlightResponse
import com.geminiboy.finalprojectbinar.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val preferences: SetDestinationPreferences,
                                        private val flightRepository: FlightRepository,
                                        private val authRepository: AuthRepository): ViewModel() {
    private val _favDestination = MutableLiveData<Resource<FlightResponse>>()
    val favDestination: LiveData<Resource<FlightResponse>> get() = _favDestination

    fun getToken() = authRepository.getToken().asLiveData()
    fun getPassenger(): LiveData<Int> = preferences.getPassenger().asLiveData()
    fun getDateDeparture(): LiveData<String> = preferences.getDateDeparture().asLiveData()
    fun getDateReturn(): LiveData<String> = preferences.getDateReturn().asLiveData()
    fun getSeatClass(): LiveData<String> = preferences.getSeat().asLiveData()
    fun clear() = viewModelScope.launch(Dispatchers.IO){
        preferences.clearPreferences()
    }

    fun getFlight(token: String) {
        _favDestination.postValue(Resource.Loading())
        viewModelScope.launch {
            _favDestination.postValue(flightRepository.getFlight("Bearer $token"))
        }
    }
}