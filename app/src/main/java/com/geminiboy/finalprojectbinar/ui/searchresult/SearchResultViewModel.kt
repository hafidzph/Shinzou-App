package com.geminiboy.finalprojectbinar.ui.searchresult

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.geminiboy.finalprojectbinar.data.local.datastore.SetDestinationPreferences
import com.geminiboy.finalprojectbinar.data.repository.FlightRepository
import com.geminiboy.finalprojectbinar.model.flight.SearchFlightResponse
import com.geminiboy.finalprojectbinar.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchResultViewModel @Inject constructor(private val preferences: SetDestinationPreferences,
                                                private val flightRepository: FlightRepository): ViewModel() {
    private val _search = MutableLiveData<Resource<SearchFlightResponse>>()
    val search: LiveData<Resource<SearchFlightResponse>> get() = _search
    fun getDateDeparture(): LiveData<String> = preferences.getDateDeparture().asLiveData()
    fun getDateReturn(): LiveData<String> = preferences.getDateReturn().asLiveData()
    fun getDestinationCode(): LiveData<String> = preferences.getCodeCityJoin().asLiveData()
    fun getDestinationCodeReturn(): LiveData<String> = preferences.getCodeCityJoinReturn().asLiveData()
    fun getPassengerJoinSeat(): LiveData<String> = preferences.getPassengerJoinSeat().asLiveData()
    fun setDepartureId(id: String) = viewModelScope.launch { flightRepository.setDepartureId(id) }
    fun setReturnId(id: String) = viewModelScope.launch { flightRepository.setReturnId(id) }
    fun setDeparturePrice(price: Int) = viewModelScope.launch { flightRepository.setDeparturePrice(price) }
    fun setReturnPrice(price: Int) = viewModelScope.launch { flightRepository.setReturnPrice(price) }
    fun getSearchDeparture(date: String) {
        _search.postValue(Resource.Loading())
        viewModelScope.launch {
            _search.postValue(flightRepository.getSearchFlight(
                preferences.getFrom().first(),
                preferences.getTo().first(),
                date,
                preferences.getPassenger().first(),
                preferences.getSeat().first()))
        }
    }

    fun getSearchReturn(date: String) {
        _search.postValue(Resource.Loading())
        viewModelScope.launch {
            _search.postValue(flightRepository.getSearchFlight(
                preferences.getTo().first(),
                preferences.getFrom().first(),
                date,
                preferences.getPassenger().first(),
                preferences.getSeat().first()))
        }
    }
}