package com.flightapp.service;

import com.flightapp.model.dto.BookingRequest;
import com.flightapp.model.dto.BookingResponse;
import com.flightapp.model.enums.BookingStatus;
import com.flightapp.exception.BookingException;
import com.flightapp.exception.ResourceNotFoundException;
import com.flightapp.model.entity.Booking;
import com.flightapp.repository.BookingRepository;
import com.flightapp.service.BookingService;
import com.flightapp.service.FlightService;
import com.flightapp.util.PnrGenerator;
import com.flightapp.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImpl implements BookingService {
    
    private final BookingRepository bookingRepository;
    private final FlightService flightService;
    private final PnrGenerator pnrGenerator;
    private final ModelMapper modelMapper;
    
    @Override
    public Mono<BookingResponse> bookTicket(String flightId, BookingRequest bookingRequest) {
        log.info("Booking ticket for flight: {}", flightId);
        
        return flightService.getFlightById(flightId)
                .flatMap(flight -> {
                    // Validate seat numbers
                    boolean validSeats = bookingRequest.getPassengers().stream()
                            .allMatch(p -> ValidationUtil.isValidSeatNumber(p.getSeatNumber()));
                    
                    if (!validSeats) {
                        return Mono.error(new BookingException("Invalid seat number format"));
                    }
                    
                    int seatsToBook = bookingRequest.getPassengers().size();
                    
                    return flightService.updateAvailableSeats(flightId, seatsToBook)
                            .flatMap(updatedFlight -> {
                                Booking booking = new Booking();
                                booking.setPnr(pnrGenerator.generatePnr());
                                booking.setFlightId(flightId);
                                booking.setUserName(bookingRequest.getUserName());
                                booking.setEmail(bookingRequest.getEmail());
                                booking.setPassengers(bookingRequest.getPassengers());
                                booking.setStatus(BookingStatus.CONFIRMED);
                                booking.setBookingDate(LocalDateTime.now());
                                booking.setTotalPrice(updatedFlight.getPrice() * seatsToBook);
                                
                                // Store flight details for reference
                                booking.setAirlineName(updatedFlight.getAirlineName());
                                booking.setFromLocation(updatedFlight.getFromLocation());
                                booking.setToLocation(updatedFlight.getToLocation());
                                booking.setDepartureTime(updatedFlight.getDepartureTime());
                                
                                return bookingRepository.save(booking);
                            });
                })
                .map(booking -> modelMapper.map(booking, BookingResponse.class))
                .doOnSuccess(response -> 
                    log.info("Booking completed successfully. PNR: {}", response.getPnr()))
                .doOnError(error -> 
                    log.error("Error during booking: {}", error.getMessage()));
    }
    
    @Override
    public Mono<BookingResponse> getTicketByPnr(String pnr) {
        log.info("Fetching ticket with PNR: {}", pnr);
        
        return bookingRepository.findByPnr(pnr)
                .switchIfEmpty(Mono.error(
                    new ResourceNotFoundException("Booking not found with PNR: " + pnr)))
                .map(booking -> modelMapper.map(booking, BookingResponse.class));
    }
    
    @Override
    public Flux<BookingResponse> getBookingHistory(String emailId) {
        log.info("Fetching booking history for email: {}", emailId);
        
        return bookingRepository.findByEmail(emailId)
                .map(booking -> modelMapper.map(booking, BookingResponse.class))
                .switchIfEmpty(Flux.empty());
    }
    
    @Override
    public Mono<Void> cancelBooking(String pnr) {
        log.info("Cancelling booking with PNR: {}", pnr);
        
        return bookingRepository.findByPnr(pnr)
                .switchIfEmpty(Mono.error(
                    new ResourceNotFoundException("Booking not found with PNR: " + pnr)))
                .flatMap(booking -> {
                    if (booking.getStatus() == BookingStatus.CANCELLED) {
                        return Mono.error(new BookingException("Booking already cancelled"));
                    }
                    
                    if (!ValidationUtil.canCancelBooking(booking.getDepartureTime())) {
                        return Mono.error(new BookingException(
                            "Cannot cancel booking. Cancellation allowed only 24 hours before departure"));
                    }
                    
                    // Update booking status
                    booking.setStatus(BookingStatus.CANCELLED);
                    
                    return bookingRepository.save(booking)
                            .flatMap(cancelledBooking -> {
                                // Return seats to flight
                                int seatsToReturn = cancelledBooking.getPassengers().size();
                                return flightService.getFlightById(cancelledBooking.getFlightId())
                                        .flatMap(flight -> {
                                            flight.setAvailableSeats(
                                                flight.getAvailableSeats() + seatsToReturn);
                                            return flightService.addFlightInventory(flight);
                                        });
                            })
                            .then();
                })
                .doOnSuccess(v -> log.info("Booking cancelled successfully: {}", pnr))
                .doOnError(error -> log.error("Error cancelling booking: {}", error.getMessage()));
    }
}