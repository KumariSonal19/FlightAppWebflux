package com.flightapp.model.entity;

import com.flightapp.model.enums.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "bookings")
public class Booking {

    @Id
    private String id;

    @NotBlank(message = "PNR is required")
    private String pnr;

    @NotBlank(message = "Flight ID is required")
    private String flightId;

    @NotBlank(message = "User name is required")
    private String userName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotEmpty(message = "At least one passenger is required")
    @Valid
    private List<Passenger> passengers;

    @NotNull(message = "Total price is required")
    private Double totalPrice;

    @NotNull(message = "Booking status is required")
    private BookingStatus status;

    private LocalDateTime bookingDate;
    private LocalDateTime journeyDate;
    private LocalDateTime cancellationDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private String airlineName;
    private String fromLocation;
    private String toLocation;
    private LocalDateTime departureTime;
}
