package com.geminiboy.finalprojectbinar.ui.auth.register

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RegisterViewModel: ViewModel() {
    private var _isValid = MutableLiveData<Boolean>()
    val isValid: LiveData<Boolean> = _isValid

    fun validateEmail(email: String){
        val validEmail = if(email.isNotEmpty()){
            false
        }else{
            Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }
        _isValid.postValue(validEmail)
    }

    fun validatePassword(password: String){
        val validPassword = if(password.isNotEmpty()) false else password.length <= 8
        _isValid.postValue(validPassword)
    }

    fun validateName(name: String){
        val validName = name.isNotEmpty()
        _isValid.postValue(validName)
    }

    fun phoneNumber(number: String){
        val validNumber = number.isNotEmpty()
        _isValid.postValue(validNumber)
    }
}