package com.photohire.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Data
@Getter
@Setter
public class PhotographerProfileRequest {

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "Bio is required")
    private String bio;

    @NotNull(message = "Daily rate is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Daily rate must be greater than zero")
    private BigDecimal dailyRate;

    @NotNull(message = "Years of experience is required")
    @PositiveOrZero(message = "Years of experience cannot be negative")
    private Integer yearsOfExperience;

    @NotNull(message = "Availability is required")
    private Boolean available;

    @NotNull(message = "User ID is required")
    private Long userId;
}