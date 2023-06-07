package com.geminiboy.finalprojectbinar.ui.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel: ViewModel() {
    private var _isValidEmailPhone = MutableLiveData<Boolean>()
    val isValidEmailPhone: LiveData<Boolean> = _isValidEmailPhone
}