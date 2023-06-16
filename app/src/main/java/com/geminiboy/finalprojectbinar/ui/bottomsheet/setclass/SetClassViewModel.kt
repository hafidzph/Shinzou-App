package com.geminiboy.finalprojectbinar.ui.bottomsheet.setclass

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geminiboy.finalprojectbinar.data.local.datastore.SetDestinationPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SetClassViewModel @Inject constructor(private val preferences: SetDestinationPreferences): ViewModel(){
    fun setSeat(seat: String) = viewModelScope.launch(Dispatchers.IO) {
        preferences.setSeat(seat)
    }
}