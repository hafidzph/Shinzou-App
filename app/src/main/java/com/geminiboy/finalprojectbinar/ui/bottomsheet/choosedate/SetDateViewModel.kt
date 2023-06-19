package com.geminiboy.finalprojectbinar.ui.bottomsheet.choosedate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geminiboy.finalprojectbinar.data.local.datastore.SetDestinationPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SetDateViewModel @Inject constructor(private val preferences: SetDestinationPreferences): ViewModel() {
    fun setDepartureDate(date: String) = viewModelScope.launch(Dispatchers.IO) { preferences.setDateDeparture(date) }
    fun setReturnDate(date: String) = viewModelScope.launch(Dispatchers.IO){ preferences.setReturnDeparture(date) }
}