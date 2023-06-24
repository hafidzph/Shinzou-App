package com.geminiboy.finalprojectbinar.ui.register

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geminiboy.finalprojectbinar.data.repository.AuthRepository
import com.geminiboy.finalprojectbinar.model.user.RegisterBody
import com.geminiboy.finalprojectbinar.model.user.RegisterResponse
import com.geminiboy.finalprojectbinar.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val authRepository: AuthRepository): ViewModel() {
    private var _isValidName = MutableLiveData<Boolean>()
    val isValidName: LiveData<Boolean> get() = _isValidName

    private var _isValidEmail = MutableLiveData<Boolean>()
    val isValidEmail: LiveData<Boolean> get() = _isValidEmail

    private var _isValidPassword = MutableLiveData<Boolean>()
    val isValidPassword: LiveData<Boolean> get() = _isValidPassword

    private var _isValidNoTelp = MutableLiveData<Boolean>()
    val isValidNoTelp: LiveData<Boolean> get() = _isValidNoTelp

    private val _userRegister = MutableLiveData<Resource<RegisterResponse>>()
    val userRegister: LiveData<Resource<RegisterResponse>> get() = _userRegister

    fun postUser(user: RegisterBody) = viewModelScope.launch(Dispatchers.IO){
        try {
            val response = authRepository.register(user)
            _userRegister.postValue(response)
        }catch (e: Exception){
            _userRegister.postValue(Resource.Error(e.message!!))
        }
    }

    fun validateEmail(email: String){
        val validEmail = email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
        _isValidEmail.postValue(validEmail)
    }

    fun validatePassword(password: String) {
        val validPassword = password.isNotEmpty() && password.length >= 8
        _isValidPassword.postValue(validPassword)
    }

    fun validateName(name: String){
        val validName = name.isNotEmpty()
        _isValidName.postValue(validName)
    }

    fun phoneNumber(number: String){
        val validNumber = number.isNotEmpty()
        _isValidNoTelp.postValue(validNumber)
    }
}