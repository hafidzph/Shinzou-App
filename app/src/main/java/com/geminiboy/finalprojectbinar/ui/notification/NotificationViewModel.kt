package com.geminiboy.finalprojectbinar.ui.notification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.geminiboy.finalprojectbinar.data.repository.AuthRepository
import com.geminiboy.finalprojectbinar.model.notification.NotificationResponse
import com.geminiboy.finalprojectbinar.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(private val authRepository: AuthRepository): ViewModel() {
    private val _notification = MutableLiveData<Resource<NotificationResponse>>()
    val notification: LiveData<Resource<NotificationResponse>> get() = _notification

    fun getToken() = authRepository.getToken().asLiveData()

    fun notification() {
        getToken().observeForever{
            if(it.isNotEmpty()) {
                val authorization = "Bearer $it"
                viewModelScope.launch {
                    val response = authRepository.notification(authorization)
                    _notification.postValue(response)
                }
            }
        }
    }
}