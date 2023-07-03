package com.geminiboy.finalprojectbinar.model.flight


import com.google.gson.annotations.SerializedName

data class TransactionPostResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
) {
    data class Data(
        @SerializedName("ammount")
        val ammount: Int,
        @SerializedName("booking_code")
        val bookingCode: String,
        @SerializedName("createdAt")
        val createdAt: String,
        @SerializedName("departure_flight_id")
        val departureFlightId: String,
        @SerializedName("id")
        val id: String,
        @SerializedName("payment_method")
        val paymentMethod: Any,
        @SerializedName("return_flight_id")
        val returnFlightId: Any,
        @SerializedName("updatedAt")
        val updatedAt: String,
        @SerializedName("user_id")
        val userId: String
    )
}