package com.flightapp.service;

import com.flightapp.model.dto.BookingRequest;
import com.flightapp.model.dto.BookingResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BookingService {
    
    Mono<BookingResponse> bookTicket(String flightId, BookingRequest bookingRequest);
    
    Mono<BookingResponse> getTicketByPnr(String pnr);
    
    Flux<BookingResponse> getBookingHistory(String emailId);
    
    Mono<Void> cancelBooking(String pnr);
}