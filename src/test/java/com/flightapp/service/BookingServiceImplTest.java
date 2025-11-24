package com.flightapp.service;

import com.flightapp.model.dto.BookingRequest;
import com.flightapp.model.dto.BookingResponse;
import com.flightapp.model.enums.BookingStatus;
import com.flightapp.model.enums.Gender;
import com.flightapp.model.enums.MealPreference;
import com.flightapp.exception.ResourceNotFoundException;
import com.flightapp.model.entity.Booking;
import com.flightapp.model.entity.Flight;
import com.flightapp.model.entity.Passenger;
import com.flightapp.repository.BookingRepository;
import com.flightapp.service.FlightService;
import com.flightapp.util.PnrGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingServiceImplTest {
    
    @Mock
    private BookingRepository bookingRepository;
    
    @Mock
    private FlightService flightService;
    
    @Mock
    private PnrGenerator pnrGenerator;
    
    @Mock
    private ModelMapper modelMapper;
    
    @InjectMocks
    private BookingServiceImpl bookingService;
    
    private Flight testFlight;
    private Booking testBooking;
    private BookingRequest bookingRequest;
    private BookingResponse bookingResponse;
    
    @BeforeEach
    void setUp() {
        testFlight = new Flight();
        testFlight.setId("1");
        testFlight.setAirlineName("Test Airlines");
        testFlight.setFromLocation("Delhi");
        testFlight.setToLocation("Mumbai");
        testFlight.setDepartureTime(LocalDateTime.now().plusDays(2));
        testFlight.setPrice(5000.0);
        testFlight.setAvailableSeats(100);
        
        Passenger passenger = new Passenger();
        passenger.setName("John Doe");
        passenger.setGender(Gender.MALE);
        passenger.setAge(30);
        passenger.setSeatNumber("A1");
        passenger.setMealPreference(MealPreference.VEG);
        
        List<Passenger> passengers = Arrays.asList(passenger);
        
        bookingRequest = new BookingRequest();
        bookingRequest.setUserName("John Doe");
        bookingRequest.setEmail("john@example.com");
        bookingRequest.setPassengers(passengers);
        
        testBooking = new Booking();
        testBooking.setId("1");
        testBooking.setPnr("FLT2411241ABCD");
        testBooking.setFlightId("1");
        testBooking.setUserName("John Doe");
        testBooking.setEmail("john@example.com");
        testBooking.setPassengers(passengers);
        testBooking.setStatus(BookingStatus.CONFIRMED);
        testBooking.setBookingDate(LocalDateTime.now());
        testBooking.setTotalPrice(5000.0);
        testBooking.setDepartureTime(LocalDateTime.now().plusDays(2));
        
        bookingResponse = new BookingResponse();
        bookingResponse.setPnr("FLT2411241ABCD");
        bookingResponse.setStatus(BookingStatus.CONFIRMED);
    }
    
    @Test
    void testBookTicket_Success() {
        when(flightService.getFlightById("1")).thenReturn(Mono.just(testFlight));
        when(pnrGenerator.generatePnr()).thenReturn("FLT2411241ABCD");
        when(flightService.updateAvailableSeats(anyString(), anyInt()))
                .thenReturn(Mono.just(testFlight));
        when(bookingRepository.save(any(Booking.class))).thenReturn(Mono.just(testBooking));
        when(modelMapper.map(any(Booking.class), any())).thenReturn(bookingResponse);
        
        StepVerifier.create(bookingService.bookTicket("1", bookingRequest))
                .expectNextMatches(response -> response.getPnr().equals("FLT2411241ABCD"))
                .verifyComplete();
    }
    
    @Test
    void testGetTicketByPnr_Success() {
        when(bookingRepository.findByPnr("FLT2411241ABCD"))
                .thenReturn(Mono.just(testBooking));
        when(modelMapper.map(any(Booking.class), any())).thenReturn(bookingResponse);
        
        StepVerifier.create(bookingService.getTicketByPnr("FLT2411241ABCD"))
                .expectNextMatches(response -> response.getPnr().equals("FLT2411241ABCD"))
                .verifyComplete();
    }
    
    @Test
    void testGetTicketByPnr_NotFound() {
        when(bookingRepository.findByPnr("INVALID")).thenReturn(Mono.empty());
        
        StepVerifier.create(bookingService.getTicketByPnr("INVALID"))
                .expectError(ResourceNotFoundException.class)
                .verify();
    }
    
    @Test
    void testGetBookingHistory() {
        when(bookingRepository.findByEmail("john@example.com"))
                .thenReturn(Flux.just(testBooking));
        when(modelMapper.map(any(Booking.class), any())).thenReturn(bookingResponse);
        
        StepVerifier.create(bookingService.getBookingHistory("john@example.com"))
                .expectNextCount(1)
                .verifyComplete();
    }
}