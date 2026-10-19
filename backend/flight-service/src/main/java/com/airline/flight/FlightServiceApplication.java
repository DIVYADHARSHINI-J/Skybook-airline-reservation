package com.airline.flight;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FlightServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(FlightServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner seedFlights(FlightRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.save(new Flight("AI101", "Air India", "Chennai", "Delhi", "2026-11-02 08:30", 6200.00, 35));
                repository.save(new Flight("6E204", "IndiGo", "Chennai", "Mumbai", "2026-11-02 11:15", 4800.00, 22));
                repository.save(new Flight("SG510", "SpiceJet", "Bengaluru", "Hyderabad", "2026-11-03 16:00", 3900.00, 18));
                repository.save(new Flight("AI220", "Air India", "Delhi", "Chennai", "2026-11-04 19:45", 6700.00, 28));
            }
        };
    }
}
