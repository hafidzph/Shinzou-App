package com.geminiboy.finalprojectbinar.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.geminiboy.finalprojectbinar.data.local.datastore.SetDestinationPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val preferences: SetDestinationPreferences): ViewModel() {
    fun getPassenger(): LiveData<Int> = preferences.getPassenger().asLiveData()
    fun getDateDeparture(): LiveData<String> = preferences.getDateDeparture().asLiveData()
    fun getDateReturn(): LiveData<String> = preferences.getDateReturn().asLiveData()
    fun getSeatClass(): LiveData<String> = preferences.getSeat().asLiveData()
    fun clear() = viewModelScope.launch(Dispatchers.IO){
        preferences.clearPreferences()
    }
}