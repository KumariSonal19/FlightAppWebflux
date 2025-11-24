package com.flightapp.controller;

import com.flightapp.exception.BookingException;
import com.flightapp.exception.ResourceNotFoundException;
import com.flightapp.model.dto.BookingRequest;
import com.flightapp.model.dto.BookingResponse;
import com.flightapp.model.entity.Passenger;
import com.flightapp.model.enums.Gender;
import com.flightapp.model.enums.MealPreference;
import com.flightapp.service.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebFluxTest(BookingController.class)
class BookingControllerNegativeTest {

    @Autowired
    private WebTestClient client;

    @MockBean
    private BookingService bookingService;

    private BookingRequest request;

    @BeforeEach
    void init() {
        Passenger p = new Passenger("John", Gender.MALE, 22, "A1", MealPreference.VEG);
        request = new BookingRequest("John", "john@a.com", List.of(p));
    }

    @Test
    void testBookTicket_BookingException() {
        when(bookingService.bookTicket(any(), any()))
                .thenReturn(Mono.error(new BookingException("Failed")));

        client.post()
                .uri("/api/flight/booking/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void testGetTicketByPnr_NotFound() {
        when(bookingService.getTicketByPnr("X"))
                .thenReturn(Mono.error(new ResourceNotFoundException("Not found")));

        client.get()
                .uri("/api/flight/ticket/X")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testCancelBooking_BookingException() {
        when(bookingService.cancelBooking("X"))
                .thenReturn(Mono.error(new BookingException("Cannot cancel")));

        client.delete()
                .uri("/api/flight/booking/cancel/X")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.success").isEqualTo(false);
    }

    @Test
    void testHistory_EmptyList() {
        when(bookingService.getBookingHistory("email"))
                .thenReturn(Flux.empty());

        client.get()
                .uri("/api/flight/booking/history/email")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(BookingResponse.class)
                .hasSize(0);
    }
}
