package com.geminiboy.finalprojectbinar.data.repository

import com.geminiboy.finalprojectbinar.data.local.datastore.UserPreferences
import com.geminiboy.finalprojectbinar.data.remote.service.UserService
import com.geminiboy.finalprojectbinar.model.notification.NotificationResponse
import com.geminiboy.finalprojectbinar.model.otp.OtpBody
import com.geminiboy.finalprojectbinar.model.otp.OtpResponse
import com.geminiboy.finalprojectbinar.model.otp.ResendOtpResponse
import com.geminiboy.finalprojectbinar.model.user.ForgotPasswordBody
import com.geminiboy.finalprojectbinar.model.user.ForgotPasswordResponse
import com.geminiboy.finalprojectbinar.model.user.LoginBody
import com.geminiboy.finalprojectbinar.model.user.LoginResponse
import com.geminiboy.finalprojectbinar.model.user.RegisterBody
import com.geminiboy.finalprojectbinar.model.user.RegisterResponse
import com.geminiboy.finalprojectbinar.model.user.UpdateProfileBody
import com.geminiboy.finalprojectbinar.model.user.UpdateProfileResponse
import com.geminiboy.finalprojectbinar.wrapper.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface AuthRepository {
    suspend fun register(body: RegisterBody): Resource<RegisterResponse>
    suspend fun login(body: LoginBody): Resource<LoginResponse>
    suspend fun otp(otp: OtpBody): Resource<OtpResponse>
    suspend fun resendOtp(id: String): Resource<ResendOtpResponse>
    suspend fun forgotPassword(body: ForgotPasswordBody): Resource<ForgotPasswordResponse>
    suspend fun notification(token: String): Resource<NotificationResponse>
    suspend fun updateProfile(token: String, id: String, body: UpdateProfileBody): Resource<UpdateProfileResponse>
    fun getToken(): Flow<String>
    fun getNameUser(): Flow<String>
    fun getPhoneUser(): Flow<String>
    fun getEmailUser(): Flow<String>
    fun getIdUser(): Flow<String>
    suspend fun setToken(token: String)
    suspend fun setNameUser(name: String)
    suspend fun setPhoneUser(phone: String)
    suspend fun setEmailUser(email: String)
    suspend fun setIdUser(id: String)
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

    override suspend fun resendOtp(id: String): Resource<ResendOtpResponse> {
        return try {
            val response = api.resendOtp(id)
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

    override suspend fun notification(token: String): Resource<NotificationResponse> {
        return try {
            val response = api.getNotification(token)
            Resource.Success(response)
        }catch (e: Exception){
            Resource.Error(e.message!!)
        }
    }

    override suspend fun updateProfile(
        token: String,
        id: String,
        body: UpdateProfileBody
    ): Resource<UpdateProfileResponse> {
        return try {
            val response = api.editProfile(token, id, body)
            Resource.Success(response)
        }catch (e: Exception){
            Resource.Error(e.message!!)
        }
    }

    override fun getToken(): Flow<String> = userPreferences.getToken()
    override fun getNameUser(): Flow<String> {
        return userPreferences.getName()
    }

    override fun getPhoneUser(): Flow<String> {
        return userPreferences.getPhone()
    }

    override fun getEmailUser(): Flow<String> {
        return userPreferences.getEmail()
    }

    override fun getIdUser(): Flow<String> = userPreferences.getId()

    override suspend fun setToken(token: String) = userPreferences.setToken(token)
    override suspend fun setNameUser(name: String) {
        userPreferences.setNameUser(name)
    }

    override suspend fun setPhoneUser(phone: String) {
        userPreferences.setPhoneUser(phone)
    }

    override suspend fun setEmailUser(email: String) {
        userPreferences.emailUser(email)
    }

    override suspend fun setIdUser(id: String) = userPreferences.setIdUser(id)

    override suspend fun clearToken() = userPreferences.clearToken()

}