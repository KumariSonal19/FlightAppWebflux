package com.flightapp.service;

import com.flightapp.model.dto.FlightSearchRequest;
import com.flightapp.model.enums.TripType;
import com.flightapp.exception.ResourceNotFoundException;
import com.flightapp.model.entity.Flight;
import com.flightapp.repository.FlightRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FlightServiceImplTest {
    
    @Mock
    private FlightRepository flightRepository;
    
    @InjectMocks
    private FlightServiceImpl flightService;
    
    private Flight testFlight;
    
    @BeforeEach
    void setUp() {
        testFlight = new Flight();
        testFlight.setId("1");
        testFlight.setAirlineName("Test Airlines");
        testFlight.setAirlineLogo("https://example.com/logo.png");
        testFlight.setFromLocation("Delhi");
        testFlight.setToLocation("Mumbai");
        testFlight.setDepartureTime(LocalDateTime.now().plusDays(2));
        testFlight.setArrivalTime(LocalDateTime.now().plusDays(2).plusHours(2));
        testFlight.setPrice(5000.0);
        testFlight.setTotalSeats(100);
        testFlight.setAvailableSeats(100);
        testFlight.setFlightNumber("TA123");
    }
    
    @Test
    void testAddFlightInventory() {
        when(flightRepository.save(any(Flight.class))).thenReturn(Mono.just(testFlight));
        
        StepVerifier.create(flightService.addFlightInventory(testFlight))
                .expectNext(testFlight)
                .verifyComplete();
    }
    
    @Test
    void testSearchFlights() {
        FlightSearchRequest searchRequest = new FlightSearchRequest();
        searchRequest.setFromLocation("Delhi");
        searchRequest.setToLocation("Mumbai");
        searchRequest.setTravelDate(LocalDate.now().plusDays(2));
        searchRequest.setTripType(TripType.ONE_WAY);
        
        when(flightRepository.findByFromLocationAndToLocationAndDepartureTimeBetween(
                eq("Delhi"), eq("Mumbai"), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Flux.just(testFlight));
        
        StepVerifier.create(flightService.searchFlights(searchRequest))
                .expectNext(testFlight)
                .verifyComplete();
    }
    
    @Test
    void testGetFlightById_Success() {
        when(flightRepository.findById("1")).thenReturn(Mono.just(testFlight));
        
        StepVerifier.create(flightService.getFlightById("1"))
                .expectNext(testFlight)
                .verifyComplete();
    }
    
    @Test
    void testGetFlightById_NotFound() {
        when(flightRepository.findById("999")).thenReturn(Mono.empty());
        
        StepVerifier.create(flightService.getFlightById("999"))
                .expectError(ResourceNotFoundException.class)
                .verify();
    }
    
    @Test
    void testUpdateAvailableSeats_Success() {
        when(flightRepository.findById("1")).thenReturn(Mono.just(testFlight));
        when(flightRepository.save(any(Flight.class))).thenReturn(Mono.just(testFlight));
        
        StepVerifier.create(flightService.updateAvailableSeats("1", 5))
                .expectNextMatches(flight -> flight.getAvailableSeats() == 95)
                .verifyComplete();
    }
}