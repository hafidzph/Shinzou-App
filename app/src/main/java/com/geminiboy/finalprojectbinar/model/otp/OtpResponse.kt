package com.geminiboy.finalprojectbinar.model.otp


import com.google.gson.annotations.SerializedName

data class OtpResponse(
    @SerializedName("otp")
    val otp: String
)