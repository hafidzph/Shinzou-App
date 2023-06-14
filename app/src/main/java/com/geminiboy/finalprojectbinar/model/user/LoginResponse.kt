package com.geminiboy.finalprojectbinar.model.user


import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("response")
    val response: String,
    @SerializedName("token")
    val token: String
)