package com.geminiboy.finalprojectbinar.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.geminiboy.finalprojectbinar.data.local.datastore.UserPreferences
import com.geminiboy.finalprojectbinar.data.remote.service.UserService
import com.geminiboy.finalprojectbinar.model.notification.NotificationResponse
import com.geminiboy.finalprojectbinar.model.user.LoginBody
import com.geminiboy.finalprojectbinar.model.user.LoginResponse
import com.geminiboy.finalprojectbinar.model.user.RegisterBody
import com.geminiboy.finalprojectbinar.model.user.RegisterResponse
import com.geminiboy.finalprojectbinar.wrapper.Resource
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AuthRepositoryImplTest{

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var api: UserService

    @Mock
    private lateinit var userPreferences: UserPreferences

    private lateinit var authRepository: AuthRepository

    @Before
    fun setup() {
        authRepository = AuthRepositoryImpl(api, userPreferences)
    }

    @Test
    fun register() = runBlocking {
        val body = RegisterBody("test@gmail.com", "test", "test1234", "082421235467")
        val response = mockk<RegisterResponse>()
        Mockito.`when`(api.postUser(body)).thenReturn(response)

        val result = authRepository.register(body)

        assert(result is Resource.Success)
        assertEquals((result as Resource.Success).data, response)
    }

    @Test
    fun login() = runBlocking {
        val body = LoginBody("tesst@gmail.com", "test1234")
        val response = mockk<LoginResponse>()
        Mockito.`when`(api.postLogin(body)).thenReturn(response)

        val result = authRepository.login(body)

        assert(result is Resource.Success)
        assertEquals((result as Resource.Success).data, response)
    }

    @Test
    fun notification() = runBlocking {
        val response = mockk<NotificationResponse>()
        val token = "test"
        Mockito.`when`(api.getNotification(token)).thenReturn(response)

        val result = authRepository.notification(token)

        assert(result is Resource.Success)
        assertEquals((result as Resource.Success).data, response)
    }

    @Test
    fun setToken() = runBlocking {
        val token = "sample_token"
        authRepository.setToken(token)
        verify(userPreferences).setToken(token)
    }

    @Test
    fun setEmailUser() = runBlocking {
        val email = "test@gmail.com"
        authRepository.setEmailUser(email)
        verify(userPreferences).emailUser(email)
    }
}