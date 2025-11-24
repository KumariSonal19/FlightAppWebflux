package com.flightapp.repository;

import com.flightapp.model.entity.Flight;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

@Repository
public interface FlightRepository extends ReactiveMongoRepository<Flight, String> {
    
    Flux<Flight> findByFromLocationAndToLocationAndDepartureTimeBetween(
            String fromLocation, 
            String toLocation, 
            LocalDateTime startTime, 
            LocalDateTime endTime
    );
    
    Flux<Flight> findByFlightNumber(String flightNumber);
}