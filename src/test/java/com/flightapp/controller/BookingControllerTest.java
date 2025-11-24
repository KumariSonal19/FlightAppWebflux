package com.flightapp.controller;

import com.flightapp.model.dto.BookingRequest;
import com.flightapp.model.dto.BookingResponse;
import com.flightapp.model.enums.BookingStatus;
import com.flightapp.model.enums.Gender;
import com.flightapp.model.enums.MealPreference;
import com.flightapp.model.entity.Passenger;
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

import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@WebFluxTest(BookingController.class)
class BookingControllerTest {
    
    @Autowired
    private WebTestClient webTestClient;
    
    @MockBean
    private BookingService bookingService;
    
    private BookingRequest bookingRequest;
    private BookingResponse bookingResponse;
    
    @BeforeEach
    void setUp() {
        Passenger passenger = new Passenger();
        passenger.setName("John Doe");
        passenger.setGender(Gender.MALE);
        passenger.setAge(30);
        passenger.setSeatNumber("A1");
        passenger.setMealPreference(MealPreference.VEG);
        
        bookingRequest = new BookingRequest();
        bookingRequest.setUserName("John Doe");
        bookingRequest.setEmail("john@example.com");
        bookingRequest.setPassengers(Collections.singletonList(passenger));
        
        bookingResponse = new BookingResponse();
        bookingResponse.setPnr("FLT2411241ABCD");
        bookingResponse.setFlightId("1");
        bookingResponse.setUserName("John Doe");
        bookingResponse.setEmail("john@example.com");
        bookingResponse.setPassengers(Collections.singletonList(passenger));
        bookingResponse.setStatus(BookingStatus.CONFIRMED);
        bookingResponse.setBookingDate(LocalDateTime.now());
        bookingResponse.setTotalAmount(5000.0);
    }
    
    @Test
    void testBookTicket_Success() {
        when(bookingService.bookTicket(eq("1"), any(BookingRequest.class)))
                .thenReturn(Mono.just(bookingResponse));
        
        webTestClient.post()
                .uri("/api/flight/booking/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(bookingRequest)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.success").isEqualTo(true)
                .jsonPath("$.data.pnr").isEqualTo("FLT2411241ABCD");
    }
    
    @Test
    void testGetTicketByPnr_Success() {
        when(bookingService.getTicketByPnr("FLT2411241ABCD"))
                .thenReturn(Mono.just(bookingResponse));
        
        webTestClient.get()
                .uri("/api/flight/ticket/FLT2411241ABCD")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.success").isEqualTo(true)
                .jsonPath("$.data.pnr").isEqualTo("FLT2411241ABCD");
    }
    
    @Test
    void testGetBookingHistory() {
        when(bookingService.getBookingHistory("john@example.com"))
                .thenReturn(Flux.just(bookingResponse));
        
        webTestClient.get()
                .uri("/api/flight/booking/history/john@example.com")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(BookingResponse.class)
                .hasSize(1);
    }
    
    @Test
    void testCancelBooking_Success() {
        when(bookingService.cancelBooking("FLT2411241ABCD"))
                .thenReturn(Mono.empty());
        
        webTestClient.delete()
                .uri("/api/flight/booking/cancel/FLT2411241ABCD")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.success").isEqualTo(true)
                .jsonPath("$.message").isEqualTo("Booking cancelled successfully");
    }
}