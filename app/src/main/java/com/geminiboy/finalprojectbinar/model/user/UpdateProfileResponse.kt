package com.geminiboy.finalprojectbinar.model.user


import com.google.gson.annotations.SerializedName

data class UpdateProfileResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
) {
    data class Data(
        @SerializedName("email")
        val email: String,
        @SerializedName("id")
        val id: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("phone_number")
        val phoneNumber: String,
        @SerializedName("token")
        val token: String
    )
}