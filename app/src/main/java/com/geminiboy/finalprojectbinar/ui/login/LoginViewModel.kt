package com.geminiboy.finalprojectbinar.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geminiboy.finalprojectbinar.data.repository.AuthRepository
import com.geminiboy.finalprojectbinar.model.user.LoginBody
import com.geminiboy.finalprojectbinar.model.user.LoginResponse
import com.geminiboy.finalprojectbinar.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val authRepository: AuthRepository): ViewModel() {
    private val _login = MutableLiveData<Resource<LoginResponse>>()
    val login: LiveData<Resource<LoginResponse>> get() = _login

    fun login(body: LoginBody) = viewModelScope.launch(Dispatchers.IO) {
        try {
            val response = authRepository.login(body)
            _login.postValue(response)
        }catch (e: Exception){
            _login.postValue(Resource.Error(e.message!!))
        }
    }

    fun setToken(token: String) = viewModelScope.launch {
        authRepository.setToken(token)
    }
}