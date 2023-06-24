package com.geminiboy.finalprojectbinar.data.repository

import com.geminiboy.finalprojectbinar.data.local.datastore.UserPreferences
import com.geminiboy.finalprojectbinar.data.remote.service.UserService
import com.geminiboy.finalprojectbinar.model.otp.OtpBody
import com.geminiboy.finalprojectbinar.model.otp.OtpResponse
import com.geminiboy.finalprojectbinar.model.user.ForgotPasswordBody
import com.geminiboy.finalprojectbinar.model.user.ForgotPasswordResponse
import com.geminiboy.finalprojectbinar.model.user.LoginBody
import com.geminiboy.finalprojectbinar.model.user.LoginResponse
import com.geminiboy.finalprojectbinar.model.user.RegisterBody
import com.geminiboy.finalprojectbinar.model.user.RegisterResponse
import com.geminiboy.finalprojectbinar.wrapper.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface AuthRepository {
    suspend fun register(body: RegisterBody): Resource<RegisterResponse>
    suspend fun login(body: LoginBody): Resource<LoginResponse>
    suspend fun otp(otp: OtpBody): Resource<OtpResponse>
    suspend fun forgotPassword(body: ForgotPasswordBody): Resource<ForgotPasswordResponse>
    fun getToken(): Flow<String>
    suspend fun setToken(token: String)
    suspend fun clearToken()
}

class AuthRepositoryImpl @Inject constructor(private val api: UserService, private val userPreferences: UserPreferences): AuthRepository{
    override suspend fun register(body: RegisterBody): Resource<RegisterResponse> {
        return try {
            val response = api.postUser(body)
            Resource.Success(response)
        }catch (e: Exception){
            Resource.Error(e.message!!)
        }
    }

    override suspend fun login(body: LoginBody): Resource<LoginResponse> {
        return try {
            val response = api.postLogin(body)
            Resource.Success(response)
        }catch (e: Exception){
            Resource.Error(e.message!!)
        }
    }

    override suspend fun otp(otp: OtpBody): Resource<OtpResponse> {
        return try {
            val response = api.postOTP(otp)
            Resource.Success(response)
        }catch (e: Exception){
            Resource.Error(e.message!!)
        }
    }

    override suspend fun forgotPassword(body: ForgotPasswordBody): Resource<ForgotPasswordResponse> {
        return try {
            val response = api.postForgotPassword(body)
            Resource.Success(response)
        }catch (e: Exception){
            Resource.Error(e.message!!)
        }
    }

    override fun getToken(): Flow<String> = userPreferences.getToken()

    override suspend fun setToken(token: String) = userPreferences.setToken(token)

    override suspend fun clearToken() = userPreferences.clearToken()

}