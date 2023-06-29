package com.geminiboy.finalprojectbinar.ui.riwayat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.geminiboy.finalprojectbinar.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RiwayatViewModel @Inject constructor(private val authRepository: AuthRepository): ViewModel() {
    fun getToken() = authRepository.getToken().asLiveData()
}