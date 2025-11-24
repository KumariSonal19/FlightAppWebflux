package com.flightapp.model.dto;

import com.flightapp.model.enums.BookingStatus;
import com.flightapp.model.entity.Passenger;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponse {
    private String pnr;
    private String flightId;
    private String userName;
    private String email;
    private List<Passenger> passengers;
    private BookingStatus status;
    private LocalDateTime bookingDate;
    private Double totalAmount;
    private String airlineName;
    private String fromLocation;
    private String toLocation;
    private LocalDateTime departureTime;
}