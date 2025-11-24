package com.flightapp.controller;

import com.flightapp.model.dto.ApiResponse;
import com.flightapp.model.dto.FlightInventoryRequest;
import com.flightapp.model.dto.FlightInventoryResponse;
import com.flightapp.model.dto.FlightResponseDto;
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
    public Mono<ResponseEntity<FlightInventoryResponse>> addFlightInventory(
            @Valid @RequestBody FlightInventoryRequest flightRequest) {

        log.info("REST request to add flight inventory");

        Flight flightEntity = toFlightEntity(flightRequest);

        return flightService.addFlightInventory(flightEntity)
                .map(savedFlight -> ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(new FlightInventoryResponse(
                                "Flight inventory added successfully",
                                savedFlight.getId()
                        )))
                .onErrorResume(error -> Mono.just(
                        ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(new FlightInventoryResponse(
                                        error.getMessage(),
                                        null
                                ))
                ));
    }


    @PostMapping("/search")
    public Flux<FlightResponseDto> searchFlights(
            @Valid @RequestBody FlightSearchRequest searchRequest) {

        log.info("REST request to search flights");
        return flightService.searchFlights(searchRequest)
                .map(this::toFlightResponseDto);
    }

    @GetMapping("/{flightId}")
    public Mono<ResponseEntity<ApiResponse<FlightResponseDto>>> getFlightById(
            @PathVariable String flightId) {

        log.info("REST request to get flight by ID: {}", flightId);

        return flightService.getFlightById(flightId)
                .map(flight -> ResponseEntity.ok(
                        ApiResponse.success(
                                "Flight retrieved successfully",
                                toFlightResponseDto(flight))))
                .onErrorResume(error -> Mono.just(
                        ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(ApiResponse.error(error.getMessage()))));
    }

    private Flight toFlightEntity(FlightInventoryRequest request) {
        Flight flight = new Flight();
        flight.setAirlineName(request.getAirlineName());
        flight.setAirlineLogo(request.getAirlineLogo());
        flight.setFromLocation(request.getFromLocation());
        flight.setToLocation(request.getToLocation());
        flight.setDepartureTime(request.getDepartureTime());
        flight.setArrivalTime(request.getArrivalTime());
        flight.setPrice(request.getPrice());
        flight.setTotalSeats(request.getTotalSeats());
        flight.setFlightNumber(request.getFlightNumber());
        return flight;
    }

  
    private FlightResponseDto toFlightResponseDto(Flight flight) {
        FlightResponseDto dto = new FlightResponseDto();
        dto.setId(flight.getId());
        dto.setAirlineName(flight.getAirlineName());
        dto.setAirlineLogo(flight.getAirlineLogo());
        dto.setFromLocation(flight.getFromLocation());
        dto.setToLocation(flight.getToLocation());
        dto.setDepartureTime(flight.getDepartureTime());
        dto.setArrivalTime(flight.getArrivalTime());
        dto.setPrice(flight.getPrice());
        dto.setTotalSeats(flight.getTotalSeats());
        dto.setAvailableSeats(flight.getAvailableSeats());
        dto.setFlightNumber(flight.getFlightNumber());
        return dto;
    }

}
