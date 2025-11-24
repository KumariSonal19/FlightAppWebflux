package com.flightapp.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PnrGeneratorTest {

    private final PnrGenerator generator = new PnrGenerator();

    @Test
    void testGeneratePnrFormat() {
        String pnr = generator.generatePnr();

        assertNotNull(pnr);
        assertTrue(pnr.startsWith("FLT"), "PNR should start with FLT");
        assertTrue(pnr.length() > 10, "PNR should contain prefix + date + random part");
    }

    @Test
    void testGeneratePnrUniqueness() {
        String pnr1 = generator.generatePnr();
        String pnr2 = generator.generatePnr();

        // Very small chance of collision; good enough for unit test
        assertNotEquals(pnr1, pnr2);
    }
}
