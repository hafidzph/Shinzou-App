package com.geminiboy.finalprojectbinar.ui.akun

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.geminiboy.finalprojectbinar.data.local.datastore.SetDestinationPreferences
import com.geminiboy.finalprojectbinar.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AkunViewModel @Inject constructor(private val authRepository: AuthRepository,
                                        private val preferences: SetDestinationPreferences,): ViewModel() {
    fun getToken() = authRepository.getToken().asLiveData()

    fun clear() = viewModelScope.launch(Dispatchers.IO){
        preferences.clearPreferences()
    }
}