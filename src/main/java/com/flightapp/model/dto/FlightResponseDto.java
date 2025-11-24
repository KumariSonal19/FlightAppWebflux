package com.flightapp.model.dto;

import com.flightapp.model.dto.AbstractFlightInfo;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class FlightResponseDto extends AbstractFlightInfo {

    private String id;
    private Integer totalSeats;
    private Integer availableSeats;
}
