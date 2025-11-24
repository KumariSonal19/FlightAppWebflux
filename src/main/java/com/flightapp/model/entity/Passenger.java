package com.flightapp.model.entity;

import com.flightapp.model.enums.Gender;
import com.flightapp.model.enums.MealPreference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Passenger {
    
    @NotBlank(message = "Passenger name is required")
    private String name;
    
    @NotNull(message = "Gender is required")
    private Gender gender;
    
    @NotNull(message = "Age is required")
    @Min(value = 0, message = "Age must be at least 0")
    @Max(value = 120, message = "Age must be less than 120")
    private Integer age;
    
    @NotBlank(message = "Seat number is required")
    private String seatNumber;
    
    @NotNull(message = "Meal preference is required")
    private MealPreference mealPreference;
}