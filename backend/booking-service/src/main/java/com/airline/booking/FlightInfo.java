package com.airline.booking;
public record FlightInfo(Long id,String flightNumber,String airline,String origin,String destination,
                         String departureTime, double price, int availableSeats) {}
