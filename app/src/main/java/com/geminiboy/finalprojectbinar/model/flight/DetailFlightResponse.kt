package com.geminiboy.finalprojectbinar.model.flight


import com.google.gson.annotations.SerializedName

data class DetailFlightResponse(
    @SerializedName("data")
    val `data`: Data
) {
    data class Data(
        @SerializedName("airline")
        val airline: Airline,
        @SerializedName("arrival_date")
        val arrivalDate: String,
        @SerializedName("arrival_time")
        val arrivalTime: String,
        @SerializedName("class")
        val classX: String,
        @SerializedName("departure_date")
        val departureDate: String,
        @SerializedName("departure_time")
        val departureTime: String,
        @SerializedName("description")
        val description: String,
        @SerializedName("destinationAirport")
        val destinationAirport: DestinationAirport,
        @SerializedName("flight_number")
        val flightNumber: String,
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
            @SerializedName("airport_name")
            val airportName: String,
            @SerializedName("location")
            val location: String
        )

        data class OriginAirport(
            @SerializedName("airport_name")
            val airportName: String,
            @SerializedName("location")
            val location: String
        )
    }
}