package com.airline.flight;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Entity
public class Flight {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank private String flightNumber;
    @NotBlank private String airline;
    @NotBlank private String origin;
    @NotBlank private String destination;
    @NotBlank private String departureTime;
    @NotNull @DecimalMin("0.0") private BigDecimal price;
    @Min(0) private int availableSeats;

    protected Flight() {}
    public Flight(String flightNumber, String airline, String origin, String destination,
                  String departureTime, double price, int availableSeats) {
        this.flightNumber = flightNumber; this.airline = airline; this.origin = origin;
        this.destination = destination; this.departureTime = departureTime;
        this.price = BigDecimal.valueOf(price); this.availableSeats = availableSeats;
    }
    public Long getId(){return id;}
    public String getFlightNumber(){return flightNumber;}
    public String getAirline(){return airline;}
    public String getOrigin(){return origin;}
    public String getDestination(){return destination;}
    public String getDepartureTime(){return departureTime;}
    public BigDecimal getPrice(){return price;}
    public int getAvailableSeats(){return availableSeats;}
    public void setFlightNumber(String v){flightNumber=v;}
    public void setAirline(String v){airline=v;}
    public void setOrigin(String v){origin=v;}
    public void setDestination(String v){destination=v;}
    public void setDepartureTime(String v){departureTime=v;}
    public void setPrice(BigDecimal v){price=v;}
    public void setAvailableSeats(int v){availableSeats=v;}
}
