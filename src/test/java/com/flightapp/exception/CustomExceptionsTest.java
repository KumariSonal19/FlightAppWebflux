package com.flightapp.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomExceptionsTest {

    @Test
    void testBookingExceptionMessage() {
        BookingException ex = new BookingException("Booking failed");
        assertEquals("Booking failed", ex.getMessage());
    }

    @Test
    void testResourceNotFoundExceptionMessage() {
        ResourceNotFoundException ex = new ResourceNotFoundException("Not found");
        assertEquals("Not found", ex.getMessage());
    }
}
