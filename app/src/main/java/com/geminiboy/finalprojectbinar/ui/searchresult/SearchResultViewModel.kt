package com.geminiboy.finalprojectbinar.ui.searchresult

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.geminiboy.finalprojectbinar.data.local.datastore.SetDestinationPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchResultViewModel @Inject constructor(private val preferences: SetDestinationPreferences): ViewModel() {
    fun getDate(): LiveData<String> = preferences.getDateDeparture().asLiveData()
}