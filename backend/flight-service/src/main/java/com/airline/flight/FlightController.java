package com.airline.flight;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@RequestMapping("/api/flights")
@CrossOrigin(origins = "http://localhost:5173")
public class FlightController {
    private final FlightRepository repository;
    public FlightController(FlightRepository repository){this.repository=repository;}

    @GetMapping
    public List<Flight> all(@RequestParam(required=false) String from,
                             @RequestParam(required=false) String to) {
        if (from != null && !from.isBlank() && to != null && !to.isBlank())
            return repository.findByOriginIgnoreCaseAndDestinationIgnoreCase(from.trim(), to.trim());
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Flight one(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() ->
            new ResponseStatusException(HttpStatus.NOT_FOUND, "Flight not found"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Flight create(@Valid @RequestBody Flight flight) { return repository.save(flight); }
}
