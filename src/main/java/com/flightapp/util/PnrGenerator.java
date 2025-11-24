package com.flightapp.util;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class PnrGenerator {
    
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final int PNR_LENGTH = 6;
    
    public String generatePnr() {
        StringBuilder pnr = new StringBuilder("FLT");
        
        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyMMdd"));
        pnr.append(timestamp);
        
        for (int i = 0; i < PNR_LENGTH; i++) {
            pnr.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        
        return pnr.toString();
    }
}