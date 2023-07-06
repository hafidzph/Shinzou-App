package com.geminiboy.finalprojectbinar.model.historytransaction


import com.google.gson.annotations.SerializedName

data class TransactionResponse(
    @SerializedName("data")
    val `data`: List<Data>,
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
        @SerializedName("departureFlight")
        val departureFlight: DepartureFlight,
        @SerializedName("departure_flight_id")
        val departureFlightId: String,
        @SerializedName("id")
        val id: String,
        @SerializedName("payment_method")
        val paymentMethod: String,
        @SerializedName("returnFlight")
        val returnFlight: ReturnFlight,
        @SerializedName("return_flight_id")
        val returnFlightId: Any,
        @SerializedName("tickets")
        val tickets: List<Ticket>,
        @SerializedName("updatedAt")
        val updatedAt: String,
        @SerializedName("user")
        val user: User,
        @SerializedName("user_id")
        val userId: String
    ) {
        data class DepartureFlight(
            @SerializedName("airline")
            val airline: Airline,
            @SerializedName("airline_id")
            val airlineId: Int,
            @SerializedName("arrival_date")
            val arrivalDate: String,
            @SerializedName("arrival_time")
            val arrivalTime: String,
            @SerializedName("capacity")
            val capacity: Int,
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
            @SerializedName("destination_airport_id")
            val destinationAirportId: Int,
            @SerializedName("flight_number")
            val flightNumber: String,
            @SerializedName("id")
            val id: String,
            @SerializedName("originAirport")
            val originAirport: OriginAirport,
            @SerializedName("origin_airport_id")
            val originAirportId: Int,
            @SerializedName("price")
            val price: Int
        ) {
            data class Airline(
                @SerializedName("airline_code")
                val airlineCode: String,
                @SerializedName("airline_image")
                val airlineImage: String,
                @SerializedName("airline_name")
                val airlineName: String,
                @SerializedName("createdAt")
                val createdAt: String,
                @SerializedName("id")
                val id: Int,
                @SerializedName("updatedAt")
                val updatedAt: String
            )

            data class DestinationAirport(
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

            data class OriginAirport(
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

        data class ReturnFlight(
            @SerializedName("airline")
            val airline: Airline,
            @SerializedName("airline_id")
            val airlineId: Int,
            @SerializedName("arrival_date")
            val arrivalDate: String,
            @SerializedName("arrival_time")
            val arrivalTime: String,
            @SerializedName("capacity")
            val capacity: Int,
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
            @SerializedName("destination_airport_id")
            val destinationAirportId: Int,
            @SerializedName("flight_number")
            val flightNumber: String,
            @SerializedName("id")
            val id: String,
            @SerializedName("originAirport")
            val originAirport: OriginAirport,
            @SerializedName("origin_airport_id")
            val originAirportId: Int,
            @SerializedName("price")
            val price: Int
        ) {
            data class Airline(
                @SerializedName("airline_code")
                val airlineCode: String,
                @SerializedName("airline_image")
                val airlineImage: String,
                @SerializedName("airline_name")
                val airlineName: String,
                @SerializedName("createdAt")
                val createdAt: String,
                @SerializedName("id")
                val id: Int,
                @SerializedName("updatedAt")
                val updatedAt: String
            )

            data class DestinationAirport(
                @SerializedName("airport_code")
                val airportCode: String,
                @SerializedName("airport_name")
                val airportName: String,
                @SerializedName("location")
                val location: String,
                @SerializedName("location_acronym")
                val locationAcronym: String
            )

            data class OriginAirport(
                @SerializedName("airport_code")
                val airportCode: String,
                @SerializedName("airport_name")
                val airportName: String,
                @SerializedName("location")
                val location: String,
                @SerializedName("location_acronym")
                val locationAcronym: String
            )
        }

        data class Ticket(
            @SerializedName("createdAt")
            val createdAt: String,
            @SerializedName("id")
            val id: String,
            @SerializedName("passenger")
            val passenger: Passenger,
            @SerializedName("passenger_id")
            val passengerId: String,
            @SerializedName("seat")
            val seat: Seat,
            @SerializedName("seat_id")
            val seatId: Int,
            @SerializedName("transaction_id")
            val transactionId: String,
            @SerializedName("updatedAt")
            val updatedAt: String
        ) {
            data class Passenger(
                @SerializedName("family_name")
                val familyName: Any,
                @SerializedName("identity_number")
                val identityNumber: String,
                @SerializedName("name")
                val name: String,
                @SerializedName("phone_number")
                val phoneNumber: String,
                @SerializedName("title")
                val title: String
            )

            data class Seat(
                @SerializedName("flight_id")
                val flightId: String,
                @SerializedName("id")
                val id: Int,
                @SerializedName("seat_number")
                val seatNumber: String
            )
        }

        data class User(
            @SerializedName("name")
            val name: String
        )
    }
}