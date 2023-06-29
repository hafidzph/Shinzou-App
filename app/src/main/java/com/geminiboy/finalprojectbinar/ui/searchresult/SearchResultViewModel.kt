package com.geminiboy.finalprojectbinar.ui.searchresult

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.geminiboy.finalprojectbinar.data.local.datastore.SetDestinationPreferences
import com.geminiboy.finalprojectbinar.data.repository.FlightRepository
import com.geminiboy.finalprojectbinar.model.flight.SearchFlightOneTrip
import com.geminiboy.finalprojectbinar.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class SearchResultViewModel @Inject constructor(private val preferences: SetDestinationPreferences,
                                                private val flightRepository: FlightRepository): ViewModel() {
    private val _search = MutableLiveData<Resource<SearchFlightOneTrip>>()
    val search: LiveData<Resource<SearchFlightOneTrip>> get() = _search
    fun getDate(): LiveData<String> = preferences.getDateDeparture().asLiveData()
    fun getDestinationCode(): LiveData<String> = preferences.getCodeCityJoin().asLiveData()
    fun getPassengerJoinSeat(): LiveData<String> = preferences.getPassengerJoinSeat().asLiveData()
    fun getSearch(date: String) {
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
}