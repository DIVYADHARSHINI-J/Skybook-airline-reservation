package com.airline.booking;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "https://skybook-airline-reservation-06zt.onrender.com")
public class BookingController {
 private final BookingRepository repository;
 private final RestClient flightClient;
 public BookingController(BookingRepository repository,@Value("${flight.service.url}") String flightUrl){
  this.repository=repository;this.flightClient=RestClient.builder().baseUrl(flightUrl).build();
 }
 @GetMapping public List<Booking> all(){return repository.findAll();}
 @GetMapping("/{id}") public Booking one(@PathVariable Long id){
  return repository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Booking not found"));
 }
 @PostMapping @ResponseStatus(HttpStatus.CREATED)
 public Booking create(@Valid @RequestBody CreateBookingRequest request){
  FlightInfo flight;
  try { flight=flightClient.get().uri("/api/flights/{id}",request.flightId()).retrieve().body(FlightInfo.class); }
  catch(Exception e){throw new ResponseStatusException(HttpStatus.BAD_GATEWAY,"Could not retrieve flight from Flight Service. Is it running?",e);}
  if(flight==null) throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Flight not found");
  if(flight.availableSeats()<=0) throw new ResponseStatusException(HttpStatus.CONFLICT,"No seats available");
  return repository.save(new Booking(flight.id(),flight.flightNumber(),flight.airline(),flight.origin(),
      flight.destination(),flight.departureTime(),request.passengerName().trim(),request.passengerEmail().trim()));
 }
 @DeleteMapping("/{id}") public Booking cancel(@PathVariable Long id){
  Booking booking=repository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Booking not found"));
  booking.cancel();return repository.save(booking);
 }
}
