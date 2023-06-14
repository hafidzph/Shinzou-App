package com.geminiboy.finalprojectbinar.model.user

data class RegisterBody(val name: String, val email: String,
                        val phoneNumber: String, val password: String)