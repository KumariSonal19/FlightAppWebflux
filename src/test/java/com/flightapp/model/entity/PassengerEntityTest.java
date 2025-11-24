package com.flightapp.model.entity;

import com.flightapp.model.enums.Gender;
import com.flightapp.model.enums.MealPreference;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PassengerEntityTest {

    @Test
    void testPassengerAllFields() {
        Passenger p = new Passenger();
        p.setName("Jane");
        p.setGender(Gender.FEMALE);
        p.setAge(30);
        p.setSeatNumber("B2");
        p.setMealPreference(MealPreference.NON_VEG);

        assertEquals("Jane", p.getName());
        assertEquals(Gender.FEMALE, p.getGender());
        assertEquals(30, p.getAge());
        assertEquals("B2", p.getSeatNumber());
        assertEquals(MealPreference.NON_VEG, p.getMealPreference());
    }
    @Test
    void testAllArgsEqualsHashCodeToString() {
        Passenger p1 = new Passenger("Jane", Gender.FEMALE, 30, "B2", MealPreference.NON_VEG);
        Passenger p2 = new Passenger("Jane", Gender.FEMALE, 30, "B2", MealPreference.NON_VEG);

        assertEquals(p1, p2);
        assertEquals(p1.hashCode(), p2.hashCode());

        String s = p1.toString();
        assertTrue(s.contains("Jane"));
        assertTrue(s.contains("B2"));
    }
}
