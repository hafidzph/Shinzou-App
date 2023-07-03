package com.geminiboy.finalprojectbinar.model.payment


import com.google.gson.annotations.SerializedName

data class PaymentResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)