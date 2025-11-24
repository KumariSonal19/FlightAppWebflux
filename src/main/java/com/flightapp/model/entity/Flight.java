package com.flightapp.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "flights")
public class Flight {
    
    @Id
    private String id;
    
    @NotBlank(message = "Airline name is required")
    private String airlineName;
    
    @NotBlank(message = "Airline logo URL is required")
    private String airlineLogo;
    
    @NotBlank(message = "From location is required")
    private String fromLocation;
    
    @NotBlank(message = "To location is required")
    private String toLocation;
    
    @NotNull(message = "Departure time is required")
    @Future(message = "Departure time must be in the future")
    private LocalDateTime departureTime;
    
    @NotNull(message = "Arrival time is required")
    private LocalDateTime arrivalTime;
    
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private Double price;
    
    @NotNull(message = "Total seats is required")
    @Min(value = 1, message = "Total seats must be at least 1")
    private Integer totalSeats;
    
    @NotNull(message = "Available seats is required")
    @Min(value = 0, message = "Available seats cannot be negative")
    private Integer availableSeats;
    
    @NotBlank(message = "Flight number is required")
    private String flightNumber;
}