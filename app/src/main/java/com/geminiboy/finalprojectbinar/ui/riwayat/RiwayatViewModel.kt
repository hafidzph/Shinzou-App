package com.geminiboy.finalprojectbinar.ui.riwayat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.geminiboy.finalprojectbinar.data.repository.AuthRepository
import com.geminiboy.finalprojectbinar.data.repository.FlightRepository
import com.geminiboy.finalprojectbinar.model.historytransaction.TransactionResponse
import com.geminiboy.finalprojectbinar.model.otp.ResendOtpResponse
import com.geminiboy.finalprojectbinar.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RiwayatViewModel @Inject constructor(private val authRepository: AuthRepository,
                                           private val flightRepository: FlightRepository): ViewModel() {
    private val _riwayat= MutableLiveData<Resource<TransactionResponse>>()
    val riwayat: LiveData<Resource<TransactionResponse>> get() = _riwayat
    fun getToken() = authRepository.getToken().asLiveData()

    fun getRiwayat() {
        getToken().observeForever{
            if(it != null) {
                val authorization = "Bearer $it"
                viewModelScope.launch {
                    val response = flightRepository.getTransaction(authorization)
                    _riwayat.postValue(response)
                }
            }
        }
    }
}