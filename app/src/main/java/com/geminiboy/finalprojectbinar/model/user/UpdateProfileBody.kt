package com.geminiboy.finalprojectbinar.model.user


data class UpdateProfileBody(
    val email: String,
    val name: String,
    val phone_number: String
)