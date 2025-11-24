package com.flightapp.util;

import java.time.LocalDateTime;

public class ValidationUtil {
    
    private ValidationUtil() {
        // Private constructor to prevent instantiation
    }
    
    public static boolean canCancelBooking(LocalDateTime departureTime) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime cancellationDeadline = departureTime.minusHours(24);
        return now.isBefore(cancellationDeadline);
    }
    
    public static boolean isValidSeatNumber(String seatNumber) {
        // Basic validation: seat number should be like A1, B12, etc.
        return seatNumber != null && seatNumber.matches("^[A-Z]\\d{1,2}$");
    }
}