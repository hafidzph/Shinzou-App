package com.geminiboy.finalprojectbinar.model.airport


import com.google.gson.annotations.SerializedName

data class AirportResponse(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("status")
    val status: String
) {
    data class Data(
        @SerializedName("airport_code")
        val airportCode: String,
        @SerializedName("airport_name")
        val airportName: String,
        @SerializedName("createdAt")
        val createdAt: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("location")
        val location: String,
        @SerializedName("location_acronym")
        val locationAcronym: String,
        @SerializedName("updatedAt")
        val updatedAt: String
    )
}