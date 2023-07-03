package com.geminiboy.finalprojectbinar.model.flight


data class TransactionBody(
    val departure_flight_id: String,
    val return_flight_id: Any?,
    val passengers: List<Passenger>,
    val ammount: Int
) {
    data class Passenger(
        var title: String,
        var name: String,
        var family_name: String,
        var identity_number: String,
        var phone_number: String,
        var seat_number: String
    )
}