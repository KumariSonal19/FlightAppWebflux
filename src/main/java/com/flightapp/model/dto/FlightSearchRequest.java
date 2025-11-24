package com.flightapp.model.dto;

import com.flightapp.model.enums.TripType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightSearchRequest {
    
    @NotBlank(message = "From location is required")
    private String fromLocation;
    
    @NotBlank(message = "To location is required")
    private String toLocation;
    
    @NotNull(message = "Travel date is required")
    private LocalDate travelDate;
    
    @NotNull(message = "Trip type is required")
    private TripType tripType;
    
    private LocalDate returnDate; // Optional for round trip
}