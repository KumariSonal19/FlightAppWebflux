package com.flightapp.controller;

import com.flightapp.model.dto.ApiResponse;
import com.flightapp.model.dto.FlightSearchRequest;
import com.flightapp.model.entity.Flight;
import com.flightapp.service.FlightService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/flight")
@RequiredArgsConstructor
@Slf4j
public class FlightController {
    
    private final FlightService flightService;
    
    @PostMapping("/airline/inventory")
    public Mono<ResponseEntity<ApiResponse<Flight>>> addFlightInventory(
            @Valid @RequestBody Flight flight) {
        log.info("REST request to add flight inventory");
        
        return flightService.addFlightInventory(flight)
                .map(savedFlight -> ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(ApiResponse.success("Flight inventory added successfully", savedFlight)))
                .onErrorResume(error -> Mono.just(ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.error(error.getMessage()))));
    }
    
    @PostMapping("/search")
    public Flux<Flight> searchFlights(@Valid @RequestBody FlightSearchRequest searchRequest) {
        log.info("REST request to search flights");
        return flightService.searchFlights(searchRequest);
    }
    
    @GetMapping("/{flightId}")
    public Mono<ResponseEntity<ApiResponse<Flight>>> getFlightById(@PathVariable String flightId) {
        log.info("REST request to get flight by ID: {}", flightId);
        
        return flightService.getFlightById(flightId)
                .map(flight -> ResponseEntity.ok(
                        ApiResponse.success("Flight retrieved successfully", flight)))
                .onErrorResume(error -> Mono.just(ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error(error.getMessage()))));
    }
}