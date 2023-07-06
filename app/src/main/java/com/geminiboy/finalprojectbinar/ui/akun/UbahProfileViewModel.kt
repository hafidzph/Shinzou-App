package com.geminiboy.finalprojectbinar.ui.akun

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.geminiboy.finalprojectbinar.data.repository.AuthRepository
import com.geminiboy.finalprojectbinar.model.user.UpdateProfileBody
import com.geminiboy.finalprojectbinar.model.user.UpdateProfileResponse
import com.geminiboy.finalprojectbinar.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UbahProfileViewModel @Inject constructor(private val authRepository: AuthRepository): ViewModel() {
    private val _update = MutableLiveData<Resource<UpdateProfileResponse>>()
    val update: LiveData<Resource<UpdateProfileResponse>> get() = _update

    fun getToken() = authRepository.getToken().asLiveData()
    fun getName() = authRepository.getNameUser().asLiveData()
    fun getId() = authRepository.getIdUser().asLiveData()
    fun getPhone() = authRepository.getPhoneUser().asLiveData()
    fun getEmail() = authRepository.getEmailUser().asLiveData()

    fun setNameUser(name: String) = viewModelScope.launch {
        authRepository.setNameUser(name)
    }

    fun setPhoneUser(phone: String) = viewModelScope.launch {
        authRepository.setPhoneUser(phone)
    }

    fun setEmailUser(email: String) = viewModelScope.launch {
        authRepository.setEmailUser(email)
    }

    fun updateProfile(id: String, body: UpdateProfileBody) {
        getToken().observeForever{
            val authorization = "Bearer $it"
            viewModelScope.launch {
                val response = authRepository.updateProfile(authorization, id, body)
                _update.postValue(response)
            }
        }
    }
}