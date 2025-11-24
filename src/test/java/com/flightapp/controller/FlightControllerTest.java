package com.flightapp.controller;

import com.flightapp.exception.ResourceNotFoundException;
import com.flightapp.model.dto.FlightInventoryRequest;
import com.flightapp.model.dto.FlightResponseDto;
import com.flightapp.model.dto.FlightSearchRequest;
import com.flightapp.model.entity.Flight;
import com.flightapp.model.enums.TripType;
import com.flightapp.service.FlightService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
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
    private FlightInventoryRequest inventoryRequest;

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

        inventoryRequest = new FlightInventoryRequest();
        inventoryRequest.setAirlineName(testFlight.getAirlineName());
        inventoryRequest.setAirlineLogo(testFlight.getAirlineLogo());
        inventoryRequest.setFromLocation(testFlight.getFromLocation());
        inventoryRequest.setToLocation(testFlight.getToLocation());
        inventoryRequest.setDepartureTime(testFlight.getDepartureTime());
        inventoryRequest.setArrivalTime(testFlight.getArrivalTime());
        inventoryRequest.setPrice(testFlight.getPrice());
        inventoryRequest.setTotalSeats(testFlight.getTotalSeats());
        inventoryRequest.setFlightNumber(testFlight.getFlightNumber());

    }

    @Test
    void testAddFlightInventory_Success() {
        when(flightService.addFlightInventory(any(Flight.class)))
                .thenReturn(Mono.just(testFlight));

        webTestClient.post()
                .uri("/api/flight/airline/inventory")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(inventoryRequest)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.message").isEqualTo("Flight inventory added successfully")
                .jsonPath("$.id").isEqualTo("1");
    }

    @Test
    void testAddFlightInventory_ValidationError() {
        FlightInventoryRequest invalidRequest = new FlightInventoryRequest();

        webTestClient.post()
                .uri("/api/flight/airline/inventory")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(invalidRequest)
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
                .thenReturn(Mono.error(new ResourceNotFoundException("Flight not found")));

        webTestClient.get()
                .uri("/api/flight/999")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testSearchFlights_EmptyResult() {
        FlightSearchRequest req = new FlightSearchRequest(
                "A",
                "B",
                LocalDate.now().plusDays(1),
                TripType.ONE_WAY,
                null
        );

        when(flightService.searchFlights(req)).thenReturn(Flux.empty());

        webTestClient.post()
                .uri("/api/flight/search")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(req)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(FlightResponseDto.class)
                .hasSize(0);
    }
}
