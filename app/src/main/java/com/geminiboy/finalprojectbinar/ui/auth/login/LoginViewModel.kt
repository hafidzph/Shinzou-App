package com.geminiboy.finalprojectbinar.ui.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel: ViewModel() {
    private var _isValidEmailPhone = MutableLiveData<Boolean>()
    val isValidEmailPhone: LiveData<Boolean> = _isValidEmailPhone

    private var _isValidPassword = MutableLiveData<Boolean>()
    val isValidPassword: LiveData<Boolean> = _isValidPassword

    fun validateEmailPhone(input: String){
        val validate = input.isNotEmpty()
        _isValidEmailPhone.postValue(validate)
    }

    fun validatePassword(password: String){
        val validate = password.isNotEmpty()
        _isValidPassword.postValue(validate)
    }
}