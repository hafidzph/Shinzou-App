package com.geminiboy.finalprojectbinar.ui.checkout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.geminiboy.finalprojectbinar.data.local.datastore.SetDestinationPreferences
import com.geminiboy.finalprojectbinar.data.repository.AuthRepository
import com.geminiboy.finalprojectbinar.data.repository.FlightRepository
import com.geminiboy.finalprojectbinar.model.flight.TransactionByIdResponse
import com.geminiboy.finalprojectbinar.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckoutViewModel @Inject constructor(private val flightRepository: FlightRepository,
                                            private val authRepository: AuthRepository,
                                            private val setDestinationPreferences: SetDestinationPreferences): ViewModel() {
    private val _getTransaction = MutableLiveData<Resource<TransactionByIdResponse>>()
    val getTransaction: LiveData<Resource<TransactionByIdResponse>> get() = _getTransaction

    fun getToken() = authRepository.getToken().asLiveData()
    fun getAdultCount() = setDestinationPreferences.getAdultPassenger().asLiveData()
    fun getChildCount() = setDestinationPreferences.getChildrenPassenger().asLiveData()
    fun getBabyCount() = setDestinationPreferences.getBabyPassenger().asLiveData()
    fun getPassenger() = setDestinationPreferences.getPassenger().asLiveData()
    fun getTransactionId() = flightRepository.getTransactionId().asLiveData()
    fun getPassengerAdultAndChild() = setDestinationPreferences.getPassengerAdultChild().asLiveData()
    fun getTransactionById(id: String) {
        getToken().observeForever{
            val authorization = "Bearer $it"
            viewModelScope.launch {
                val response = flightRepository.getTransactionById(authorization, id)
                _getTransaction.postValue(response)
            }
        }
    }
}