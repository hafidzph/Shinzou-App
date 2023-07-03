package com.geminiboy.finalprojectbinar.ui.biodatapenumpang

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.geminiboy.finalprojectbinar.data.local.datastore.SetDestinationPreferences
import com.geminiboy.finalprojectbinar.data.repository.AuthRepository
import com.geminiboy.finalprojectbinar.data.repository.FlightRepository
import com.geminiboy.finalprojectbinar.model.flight.TransactionBody
import com.geminiboy.finalprojectbinar.model.flight.TransactionPostResponse
import com.geminiboy.finalprojectbinar.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BiodataPenumpangViewModel @Inject constructor(private val preferences: SetDestinationPreferences,
                                                    private val flightRepository: FlightRepository,
                                                    private val authRepository: AuthRepository): ViewModel() {
    private val _addTransaction = MutableLiveData<Resource<TransactionPostResponse>>()
    val addTransaction: LiveData<Resource<TransactionPostResponse>> get() = _addTransaction

    fun getBaby() = preferences.getBabyPassenger().asLiveData()
    fun getChildren() = preferences.getChildrenPassenger().asLiveData()
    fun getAdult() = preferences.getAdultPassenger().asLiveData()
    fun getDeparture() = flightRepository.getDeparture().asLiveData()
    fun getPrice() = flightRepository.getTicketPrice().asLiveData()
    fun getToken() = authRepository.getToken().asLiveData()
    fun setTransactionId(id: String) = viewModelScope.launch { flightRepository.setTransactionId(id) }
    fun postTransaction(transactionBody: TransactionBody) {
        getToken().observeForever{
            val authorization = "Bearer $it"
            viewModelScope.launch {
                val response = flightRepository.postTransaction(authorization, transactionBody)
                _addTransaction.postValue(response)
            }
        }

    }
}