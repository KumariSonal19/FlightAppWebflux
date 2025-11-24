package com.flightapp.controller;

import com.flightapp.exception.ResourceNotFoundException;
import com.flightapp.model.dto.FlightSearchRequest;
import com.flightapp.model.entity.Flight;
import com.flightapp.model.enums.TripType;
import com.flightapp.service.FlightService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

import static org.mockito.Mockito.*;

@WebFluxTest(FlightController.class)
class FlightControllerNegativeTest {

    @Autowired
    private WebTestClient client;

    @MockBean
    private FlightService service;

    @Test
    void testAddInventory_ValidationError() {
        client.post()
                .uri("/api/flight/airline/inventory")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new Flight())
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void testGetFlightById_NotFound() {
        when(service.getFlightById("X"))
                .thenReturn(Mono.error(new ResourceNotFoundException("Not found")));

        client.get()
                .uri("/api/flight/X")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testSearchFlights_EmptyResult() {
        FlightSearchRequest req = new FlightSearchRequest("A", "B", LocalDate.now().plusDays(1), TripType.ONE_WAY, null);

        when(service.searchFlights(req)).thenReturn(Flux.empty());

        client.post()
                .uri("/api/flight/search")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(req)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Flight.class)
                .hasSize(0);
    }
}
