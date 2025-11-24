package com.flightapp.service;

import com.flightapp.exception.BookingException;
import com.flightapp.exception.ResourceNotFoundException;
import com.flightapp.model.dto.FlightSearchRequest;
import com.flightapp.model.entity.Flight;
import com.flightapp.model.enums.TripType;
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

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FlightServiceImplNegativeTest {

    @Mock
    private FlightRepository flightRepository;

    @InjectMocks
    private FlightServiceImpl service;

    private Flight flight;

    @BeforeEach
    void setup() {
        flight = new Flight();
        flight.setId("1");
        flight.setAirlineName("Indigo");
        flight.setAirlineLogo("logo");
        flight.setFromLocation("Delhi");
        flight.setToLocation("Mumbai");
        flight.setDepartureTime(LocalDateTime.now().plusDays(1));
        flight.setArrivalTime(LocalDateTime.now().plusDays(1).plusHours(2));
        flight.setPrice(4500.0);
        flight.setTotalSeats(100);
        flight.setAvailableSeats(100);
        flight.setFlightNumber("6E123");
    }


    @Test
    void testGetFlightById_NotFound() {
        when(flightRepository.findById("X")).thenReturn(Mono.empty());

        StepVerifier.create(service.getFlightById("X"))
                .expectError(ResourceNotFoundException.class)
                .verify();
    }

    @Test
    void testUpdateSeats_FlightNotFound() {
        when(flightRepository.findById("1")).thenReturn(Mono.empty());

        StepVerifier.create(service.updateAvailableSeats("1", 2))
                .expectError(ResourceNotFoundException.class)
                .verify();
    }

    @Test
    void testUpdateSeats_NotEnoughSeats() {
        flight.setAvailableSeats(1);
        when(flightRepository.findById("1")).thenReturn(Mono.just(flight));

        StepVerifier.create(service.updateAvailableSeats("1", 5))
                .expectError(BookingException.class)
                .verify();
    }

    @Test
    void testSearchFlights_NoResults() {
        FlightSearchRequest req = new FlightSearchRequest(
                "A", "B", LocalDate.now().plusDays(1), TripType.ONE_WAY, null);

        when(flightRepository.findByFromLocationAndToLocationAndDepartureTimeBetween(any(), any(), any(), any()))
                .thenReturn(Flux.empty());

        StepVerifier.create(service.searchFlights(req))
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    void testAddFlightInventory_SaveFails() {
        when(flightRepository.save(any())).thenReturn(Mono.error(new RuntimeException("DB error")));

        StepVerifier.create(service.addFlightInventory(flight))
                .expectError(RuntimeException.class)
                .verify();
    }
}
