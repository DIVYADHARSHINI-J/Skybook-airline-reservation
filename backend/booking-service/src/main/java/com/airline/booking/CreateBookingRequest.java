package com.airline.booking;
import jakarta.validation.constraints.*;
public record CreateBookingRequest(@NotNull Long flightId,
 @NotBlank @Size(max=100) String passengerName,
 @NotBlank @Email String passengerEmail) {}
