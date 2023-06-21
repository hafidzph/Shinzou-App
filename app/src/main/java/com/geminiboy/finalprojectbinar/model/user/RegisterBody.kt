package com.geminiboy.finalprojectbinar.model.user

data class RegisterBody(val email: String, val name: String,
                        val password: String, val phone_number: String)