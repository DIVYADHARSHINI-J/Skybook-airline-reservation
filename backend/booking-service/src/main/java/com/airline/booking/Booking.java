package com.airline.booking;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Booking {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 private Long flightId;
 private String flightNumber;
 private String airline;
 private String origin;
 private String destination;
 private String departureTime;
 private String passengerName;
 private String passengerEmail;
 private String status;
 private LocalDateTime bookedAt;

 protected Booking(){}
 public Booking(Long flightId,String flightNumber,String airline,String origin,String destination,
                String departureTime,String passengerName,String passengerEmail){
  this.flightId=flightId;this.flightNumber=flightNumber;this.airline=airline;this.origin=origin;
  this.destination=destination;this.departureTime=departureTime;this.passengerName=passengerName;
  this.passengerEmail=passengerEmail;this.status="CONFIRMED";this.bookedAt=LocalDateTime.now();
 }
 public Long getId(){return id;} public Long getFlightId(){return flightId;}
 public String getFlightNumber(){return flightNumber;} public String getAirline(){return airline;}
 public String getOrigin(){return origin;} public String getDestination(){return destination;}
 public String getDepartureTime(){return departureTime;} public String getPassengerName(){return passengerName;}
 public String getPassengerEmail(){return passengerEmail;} public String getStatus(){return status;}
 public LocalDateTime getBookedAt(){return bookedAt;}
 public void cancel(){status="CANCELLED";}
}
