package com.geminiboy.finalprojectbinar.data.remote.service

import com.geminiboy.finalprojectbinar.model.user.LoginBody
import com.geminiboy.finalprojectbinar.model.user.LoginResponse
import com.geminiboy.finalprojectbinar.model.user.RegisterBody
import com.geminiboy.finalprojectbinar.model.user.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {
    @POST("api/auth/signup")
    suspend fun postUser(@Body signUpBody: RegisterBody): RegisterResponse

    @POST("api/auth/signin")
    suspend fun postLogin(@Body loginBody: LoginBody): LoginResponse
}