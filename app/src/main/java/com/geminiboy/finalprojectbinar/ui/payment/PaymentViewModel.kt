package com.geminiboy.finalprojectbinar.ui.payment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.geminiboy.finalprojectbinar.data.local.datastore.SetDestinationPreferences
import com.geminiboy.finalprojectbinar.data.repository.AuthRepository
import com.geminiboy.finalprojectbinar.data.repository.FlightRepository
import com.geminiboy.finalprojectbinar.model.flight.TransactionByIdResponse
import com.geminiboy.finalprojectbinar.model.payment.PaymentBody
import com.geminiboy.finalprojectbinar.model.payment.PaymentResponse
import com.geminiboy.finalprojectbinar.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(private val authRepository: AuthRepository,
                                           private val flightRepository: FlightRepository,
                                           private val setDestinationPreferences: SetDestinationPreferences) : ViewModel() {
    private val _getTransaction = MutableLiveData<Resource<TransactionByIdResponse>>()
    val getTransaction: LiveData<Resource<TransactionByIdResponse>> get() = _getTransaction

    private val _getTransactionRoundTrip = MutableLiveData<Resource<TransactionByIdResponse>>()
    val getTransactionRoundTrip: LiveData<Resource<TransactionByIdResponse>> get() = _getTransactionRoundTrip

    private val _addPayment = MutableLiveData<Resource<PaymentResponse>>()
    val addPayment: LiveData<Resource<PaymentResponse>> get() = _addPayment

    private val passengerCount = MutableLiveData<Triple<Int, Int, Int>>()

    fun getPassengerCount(): LiveData<Triple<Int, Int, Int>> {
        return passengerCount
    }
    fun getPriceDeparture() = flightRepository.getPriceDeparture().asLiveData()
    fun getPriceReturn() = flightRepository.getPriceReturn().asLiveData()
    fun getPassengerAdultChildren() = setDestinationPreferences.getPassengerAdultChild().asLiveData()
    fun updatePassengerCount() = viewModelScope.launch {
        val adultCount = setDestinationPreferences.getAdultPassenger().first().toInt()
        val childCount = setDestinationPreferences.getChildrenPassenger().first().toInt()
        val babyCount = setDestinationPreferences.getBabyPassenger().first().toInt()

        passengerCount.value = Triple(adultCount, childCount, babyCount)
    }

    fun getTransactionId() = flightRepository.getTransactionId().asLiveData()
    fun getToken() = authRepository.getToken().asLiveData()
    fun getTransactionById(id: String) {
        getToken().observeForever{
            val authorization = "Bearer $it"
            viewModelScope.launch {
                val response = flightRepository.getTransactionById(authorization, id)
                _getTransaction.postValue(response)
            }
        }
    }

    fun getTransactionByIdRoundTrip(id: String) {
        getToken().observeForever{
            val authorization = "Bearer $it"
            viewModelScope.launch {
                val response = flightRepository.getTransactionById(authorization, id)
                _getTransactionRoundTrip.postValue(response)
            }
        }
    }

    fun addPayment(booking_code: String, paymentBody: PaymentBody){
        getToken().observeForever{
            val authorization = "Bearer $it"
            viewModelScope.launch {
                val response = flightRepository.addPayment(authorization, booking_code, paymentBody)
                _addPayment.postValue(response)
            }
        }
    }
}