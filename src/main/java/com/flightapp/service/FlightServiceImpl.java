package com.flightapp.service;

import com.flightapp.model.dto.FlightSearchRequest;
import com.flightapp.exception.BookingException;
import com.flightapp.exception.ResourceNotFoundException;
import com.flightapp.model.entity.Flight;
import com.flightapp.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class FlightServiceImpl implements FlightService {
    
    private final FlightRepository flightRepository;
    
    @Override
    public Mono<Flight> addFlightInventory(Flight flight) {
        log.info("Adding flight inventory: {}", flight.getFlightNumber());
        
     
        flight.setAvailableSeats(flight.getTotalSeats());
        
        return flightRepository.save(flight)
                .doOnSuccess(savedFlight -> 
                    log.info("Flight inventory added successfully: {}", savedFlight.getId()))
                .doOnError(error -> 
                    log.error("Error adding flight inventory: {}", error.getMessage()));
    }
    
    @Override
    public Flux<Flight> searchFlights(FlightSearchRequest searchRequest) {
        log.info("Searching flights from {} to {} on {}", 
                searchRequest.getFromLocation(), 
                searchRequest.getToLocation(), 
                searchRequest.getTravelDate());
        
        LocalDateTime startOfDay = searchRequest.getTravelDate().atStartOfDay();
        LocalDateTime endOfDay = searchRequest.getTravelDate().atTime(LocalTime.MAX);
        
        return flightRepository
                .findByFromLocationAndToLocationAndDepartureTimeBetween(
                        searchRequest.getFromLocation(),
                        searchRequest.getToLocation(),
                        startOfDay,
                        endOfDay
                )
                .filter(flight -> flight.getAvailableSeats() > 0)
                .doOnComplete(() -> log.info("Flight search completed"));
    }
    
    @Override
    public Mono<Flight> getFlightById(String flightId) {
        log.info("Fetching flight with ID: {}", flightId);
        
        return flightRepository.findById(flightId)
                .switchIfEmpty(Mono.error(
                    new ResourceNotFoundException("Flight not found with ID: " + flightId)));
    }
    
    @Override
    public Mono<Flight> updateAvailableSeats(String flightId, Integer seatsToBook) {
        log.info("Updating available seats for flight: {}", flightId);
        
        return flightRepository.findById(flightId)
                .switchIfEmpty(Mono.error(
                    new ResourceNotFoundException("Flight not found with ID: " + flightId)))
                .flatMap(flight -> {
                    if (flight.getAvailableSeats() < seatsToBook) {
                        return Mono.error(new BookingException(
                            "Not enough seats available. Available: " + flight.getAvailableSeats()));
                    }
                    
                    flight.setAvailableSeats(flight.getAvailableSeats() - seatsToBook);
                    return flightRepository.save(flight);
                })
                .doOnSuccess(updatedFlight -> 
                    log.info("Seats updated successfully. Available seats: {}", 
                        updatedFlight.getAvailableSeats()));
    }
}