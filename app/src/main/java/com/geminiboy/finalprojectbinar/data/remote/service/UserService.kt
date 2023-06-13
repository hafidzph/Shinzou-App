package com.geminiboy.finalprojectbinar.data.remote.service

import com.geminiboy.finalprojectbinar.model.user.ResponseUser
import com.geminiboy.finalprojectbinar.model.user.SignUpBody
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {
    @POST("api/auth/signup")
    suspend fun postUser(@Body signUpBody: SignUpBody): ResponseUser
}