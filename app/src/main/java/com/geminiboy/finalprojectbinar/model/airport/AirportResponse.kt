package com.geminiboy.finalprojectbinar.model.airport


import com.google.gson.annotations.SerializedName

data class AirportResponse(
    @SerializedName("data")
    val `data`: List<Data>,
) {
    data class Data(
        @SerializedName("location")
        val location: String,
        @SerializedName("location_acronym")
        val locationAcronym: String
    )
}