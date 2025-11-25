package com.flightapp.model.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FlightResponseDtoTest {

    @Test
    void testDtoFields() {
        FlightResponseDto dto = new FlightResponseDto();
        dto.setId("1");
        dto.setAirlineName("AirAsia");
        dto.setAvailableSeats(50);

        assertEquals("1", dto.getId());
        assertEquals("AirAsia", dto.getAirlineName());
        assertEquals(50, dto.getAvailableSeats());
    }
}
