package com.geminiboy.finalprojectbinar.data.repository

import com.geminiboy.finalprojectbinar.data.local.datastore.UserPreferences
import com.geminiboy.finalprojectbinar.data.remote.service.UserService
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

    override fun getToken(): Flow<String> = userPreferences.getToken()

    override suspend fun setToken(token: String) = userPreferences.setToken(token)

    override suspend fun clearToken() = userPreferences.clearToken()

}