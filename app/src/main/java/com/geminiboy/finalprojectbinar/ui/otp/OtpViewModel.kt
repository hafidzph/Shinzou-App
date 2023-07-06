package com.geminiboy.finalprojectbinar.ui.otp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geminiboy.finalprojectbinar.data.repository.AuthRepository
import com.geminiboy.finalprojectbinar.model.otp.OtpBody
import com.geminiboy.finalprojectbinar.model.otp.OtpResponse
import com.geminiboy.finalprojectbinar.model.otp.ResendOtpResponse
import com.geminiboy.finalprojectbinar.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OtpViewModel @Inject constructor(private val authRepository: AuthRepository): ViewModel() {
    private val _otp = MutableLiveData<Resource<OtpResponse>>()
    val otp: LiveData<Resource<OtpResponse>> get() = _otp

    private val _resendOtp = MutableLiveData<Resource<ResendOtpResponse>>()
    val resendOtp: LiveData<Resource<ResendOtpResponse>> get() = _resendOtp

    fun postOTP(otp: OtpBody) = viewModelScope.launch {
        try {
            val response = authRepository.otp(otp)
            _otp.postValue(response)
        }catch (e: Exception){
            _otp.postValue(Resource.Error(e.message!!))
        }
    }

    fun resendOTP(id: String) = viewModelScope.launch {
        Resource.Success(authRepository.resendOtp(id))
    }
}