package com.geminiboy.finalprojectbinar.model.otp


import com.google.gson.annotations.SerializedName

data class ResendOtpResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)