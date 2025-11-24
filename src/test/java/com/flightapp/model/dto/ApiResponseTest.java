package com.flightapp.model.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApiResponseTest {

    @Test
    void testSuccessFactory() {
        ApiResponse<String> response = ApiResponse.success("OK", "DATA");

        assertTrue(response.isSuccess());
        assertEquals("OK", response.getMessage());
        assertEquals("DATA", response.getData());
        assertNotNull(response.getTimestamp());
    }

    @Test
    void testErrorFactory() {
        ApiResponse<Object> response = ApiResponse.error("FAIL");

        assertFalse(response.isSuccess());
        assertEquals("FAIL", response.getMessage());
        assertNull(response.getData());
        assertNotNull(response.getTimestamp());
    }
    @Test
    void testAllArgsConstructorEqualsHashCodeToString() {
        ApiResponse<String> r1 = new ApiResponse<>(true, "OK", "DATA", java.time.LocalDateTime.now());
        ApiResponse<String> r2 = new ApiResponse<>(true, "OK", "DATA", r1.getTimestamp());

        assertEquals(r1, r2);
        assertEquals(r1.hashCode(), r2.hashCode());

        String s = r1.toString();
        assertTrue(s.contains("OK"));
    }
}
