package com.flightapp.service;

import com.flightapp.model.dto.FlightSearchRequest;
import com.flightapp.model.entity.Flight;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FlightService {
    
    Mono<Flight> addFlightInventory(Flight flight);
    
    Flux<Flight> searchFlights(FlightSearchRequest searchRequest);
    
    Mono<Flight> getFlightById(String flightId);
    
    Mono<Flight> updateAvailableSeats(String flightId, Integer seatsToBook);
}