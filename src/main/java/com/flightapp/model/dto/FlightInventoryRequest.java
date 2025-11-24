package com.flightapp.model.dto;

import com.flightapp.model.dto.AbstractFlightInfo;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FlightInventoryRequest extends AbstractFlightInfo {

    @NotNull(message = "Total seats is required")
    @Min(value = 1, message = "Total seats must be at least 1")
    private Integer totalSeats;
}
