package com.flightapp.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FlightInventoryResponse {
    private String message;
    private String id;
}
