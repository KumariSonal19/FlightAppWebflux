package com.flightapp.controller;

import com.flightapp.model.entity.Flight;
import com.flightapp.service.FlightService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebFluxTest(FlightController.class)
class FlightControllerTest {
    
    @Autowired
    private WebTestClient webTestClient;
    
    @MockBean
    private FlightService flightService;
    
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
    void testAddFlightInventory_Success() {
        when(flightService.addFlightInventory(any(Flight.class)))
                .thenReturn(Mono.just(testFlight));
        
        webTestClient.post()
                .uri("/api/flight/airline/inventory")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(testFlight)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.success").isEqualTo(true)
                .jsonPath("$.message").isEqualTo("Flight inventory added successfully")
                .jsonPath("$.data.airlineName").isEqualTo("Test Airlines")
                .jsonPath("$.data.flightNumber").isEqualTo("TA123");
    }
    
    @Test
    void testAddFlightInventory_ValidationError() {
        Flight invalidFlight = new Flight();
        // Missing required fields
        
        webTestClient.post()
                .uri("/api/flight/airline/inventory")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(invalidFlight)
                .exchange()
                .expectStatus().isBadRequest();
    }
    
    @Test
    void testGetFlightById_Success() {
        when(flightService.getFlightById("1")).thenReturn(Mono.just(testFlight));
        
        webTestClient.get()
                .uri("/api/flight/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.success").isEqualTo(true)
                .jsonPath("$.data.id").isEqualTo("1");
    }
    
    @Test
    void testGetFlightById_NotFound() {
        when(flightService.getFlightById("999"))
                .thenReturn(Mono.error(new com.flightapp.exception.ResourceNotFoundException(
                        "Flight not found")));
        
        webTestClient.get()
                .uri("/api/flight/999")
                .exchange()
                .expectStatus().isNotFound();
    }
}