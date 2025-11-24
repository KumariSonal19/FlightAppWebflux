package com.flightapp.controller;

import com.flightapp.model.dto.ApiResponse;
import com.flightapp.model.dto.BookingRequest;
import com.flightapp.model.dto.BookingResponse;
import com.flightapp.service.BookingService;
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
public class BookingController {
    
    private final BookingService bookingService;
    
    @PostMapping("/booking/{flightId}")
    public Mono<ResponseEntity<ApiResponse<BookingResponse>>> bookTicket(
            @PathVariable String flightId,
            @Valid @RequestBody BookingRequest bookingRequest) {
        log.info("REST request to book ticket for flight: {}", flightId);
        
        return bookingService.bookTicket(flightId, bookingRequest)
                .map(response -> ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(ApiResponse.success("Ticket booked successfully", response)))
                .onErrorResume(error -> Mono.just(ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.error(error.getMessage()))));
    }
    
    @GetMapping("/ticket/{pnr}")
    public Mono<ResponseEntity<ApiResponse<BookingResponse>>> getTicketByPnr(
            @PathVariable String pnr) {
        log.info("REST request to get ticket by PNR: {}", pnr);
        
        return bookingService.getTicketByPnr(pnr)
                .map(response -> ResponseEntity.ok(
                        ApiResponse.success("Ticket retrieved successfully", response)))
                .onErrorResume(error -> Mono.just(ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error(error.getMessage()))));
    }
    
    @GetMapping("/booking/history/{emailId}")
    public Flux<BookingResponse> getBookingHistory(@PathVariable String emailId) {
        log.info("REST request to get booking history for email: {}", emailId);
        return bookingService.getBookingHistory(emailId);
    }
    
    @DeleteMapping("/booking/cancel/{pnr}")
    public Mono<ResponseEntity<ApiResponse<Void>>> cancelBooking(@PathVariable String pnr) {
        log.info("REST request to cancel booking with PNR: {}", pnr);

        Mono<ResponseEntity<ApiResponse<Void>>> successResponse =
                Mono.just(ResponseEntity.ok(
                        ApiResponse.success("Booking cancelled successfully", null)));

        java.util.function.Function<Throwable, Mono<ResponseEntity<ApiResponse<Void>>>> errorResponse =
                error -> Mono.just(
                        ResponseEntity
                                .status(HttpStatus.NOT_FOUND)
                                .body(ApiResponse.error(error.getMessage())));

        return bookingService.cancelBooking(pnr)
                .then(successResponse)
                .onErrorResume(errorResponse);
    }

}