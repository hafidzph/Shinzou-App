package com.geminiboy.finalprojectbinar.ui.forgotpassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geminiboy.finalprojectbinar.data.repository.AuthRepository
import com.geminiboy.finalprojectbinar.model.user.ForgotPasswordBody
import com.geminiboy.finalprojectbinar.model.user.ForgotPasswordResponse
import com.geminiboy.finalprojectbinar.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(private val authRepository: AuthRepository): ViewModel() {
    private val _forgotPassword = MutableLiveData<Resource<ForgotPasswordResponse>>()
    val forgotPassword: LiveData<Resource<ForgotPasswordResponse>> get() = _forgotPassword

    fun forgotPassword(body: ForgotPasswordBody) = viewModelScope.launch {
        val response = authRepository.forgotPassword(body)
        _forgotPassword.postValue(response)
    }
}