package com.geminiboy.finalprojectbinar.ui.bottomsheet.setpassenger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geminiboy.finalprojectbinar.data.local.datastore.SetDestinationPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SetPassengerViewModel @Inject constructor(private val preferences: SetDestinationPreferences): ViewModel()  {

    fun setBabyPassenger(passenger: String) = viewModelScope.launch(Dispatchers.IO) {
        preferences.setBabyPassengers(passenger)
    }

    fun setChildrenPassenger(passenger: String) = viewModelScope.launch(Dispatchers.IO) {
        preferences.setChildrenPassengers(passenger)
    }

    fun setAdultPassenger(passenger: String) = viewModelScope.launch(Dispatchers.IO) {
        preferences.setAdultPassengers(passenger)
    }
}