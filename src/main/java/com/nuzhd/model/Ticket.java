package com.nuzhd.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record Ticket(
        @JsonProperty("origin")
        String origin,
        @JsonProperty("origin_name")
        String originName,
        @JsonProperty("destination")
        String destination,
        @JsonProperty("destination_name")
        String destinationName,
        @JsonProperty("departure_date")
        String departureDate,
        @JsonProperty("departure_time")
        String departureTime,
        @JsonProperty("arrival_date")
        String arrivalDate,
        @JsonProperty("arrival_time")
        String arrivalTime,
        @JsonProperty("carrier")
        String carrier,
        @JsonProperty("stops")
        Integer stops,
        @JsonProperty("price")
        BigDecimal price
) {
    public Duration getFlightDuration() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy H:mm");

        LocalDateTime departureTime = LocalDateTime.parse(this.departureDate + " " + this.departureTime, formatter);
        LocalDateTime arrivalTime = LocalDateTime.parse(this.arrivalDate + " " + this.arrivalTime, formatter);

        return Duration.between(departureTime, arrivalTime);
    }

}
