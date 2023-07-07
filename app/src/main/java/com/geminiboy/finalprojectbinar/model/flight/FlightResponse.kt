package com.geminiboy.finalprojectbinar.model.flight


import com.google.gson.annotations.SerializedName

data class FlightResponse(
    @SerializedName("data")
    val `data`: List<Data>
) {
    data class Data(
        @SerializedName("airline")
        val airline: Airline,
        @SerializedName("arrival_date")
        val arrivalDate: String,
        @SerializedName("departure_date")
        val departureDate: String,
        @SerializedName("destinationAirport")
        val destinationAirport: DestinationAirport,
        @SerializedName("originAirport")
        val originAirport: OriginAirport,
        @SerializedName("price")
        val price: Int
    ) {
        data class Airline(
            @SerializedName("airline_name")
            val airlineName: String
        )

        data class DestinationAirport(
            @SerializedName("location")
            val location: String
        )

        data class OriginAirport(
            @SerializedName("location")
            val location: String
        )
    }
}