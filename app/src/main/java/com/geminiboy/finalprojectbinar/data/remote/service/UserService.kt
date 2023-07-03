package com.geminiboy.finalprojectbinar.data.remote.service

import com.geminiboy.finalprojectbinar.model.otp.OtpBody
import com.geminiboy.finalprojectbinar.model.otp.OtpResponse
import com.geminiboy.finalprojectbinar.model.user.ForgotPasswordBody
import com.geminiboy.finalprojectbinar.model.user.ForgotPasswordResponse
import com.geminiboy.finalprojectbinar.model.user.LoginBody
import com.geminiboy.finalprojectbinar.model.user.LoginResponse
import com.geminiboy.finalprojectbinar.model.user.RegisterBody
import com.geminiboy.finalprojectbinar.model.user.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {
    @POST("register")
    suspend fun postUser(@Body signUpBody: RegisterBody): RegisterResponse

    @POST("verify")
    suspend fun postOTP(@Body otp: OtpBody): OtpResponse

    @POST("login")
    suspend fun postLogin(@Body loginBody: LoginBody): LoginResponse

    @POST("forgot-password")
    suspend fun postForgotPassword(@Body forgotPasswordBody: ForgotPasswordBody): ForgotPasswordResponse
}