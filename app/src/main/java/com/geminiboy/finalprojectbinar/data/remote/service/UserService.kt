package com.geminiboy.finalprojectbinar.data.remote.service

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
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserService {
    @POST("register")
    suspend fun postUser(@Body signUpBody: RegisterBody): RegisterResponse

    @POST("verify")
    suspend fun postOTP(@Body otp: OtpBody): OtpResponse

    @GET("notification")
    suspend fun getNotification(@Header("Authorization") authorization: String): NotificationResponse

    @POST("resend-otp/{id}")
    suspend fun resendOtp(@Path("id") id: String): ResendOtpResponse

    @POST("login")
    suspend fun postLogin(@Body loginBody: LoginBody): LoginResponse
    @PUT("user/{id}")
    suspend fun editProfile(@Header("Authorization") authorization: String, @Path("id") id: String, @Body updateProfileBody: UpdateProfileBody): UpdateProfileResponse

    @POST("forgot-password")
    suspend fun postForgotPassword(@Body forgotPasswordBody: ForgotPasswordBody): ForgotPasswordResponse
}