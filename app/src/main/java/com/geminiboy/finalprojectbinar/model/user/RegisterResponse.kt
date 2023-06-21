package com.geminiboy.finalprojectbinar.model.user


import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("email")
    val email: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("phone_number")
    val phoneNumber: String
)