package com.photohire.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Data
@Getter
@Setter
public class BookingRequest {

    @NotBlank(message = "Location is required")
    private String location;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    private LocalDate endDate;

    @NotNull(message = "Total days is required")
    @Positive(message = "Total days must be greater than zero")
    private Integer totalDays;

    @NotNull(message = "Expected budget is required")
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal expectedBudget;

    @NotNull(message = "Client ID is required")
    private Long clientId;

    @NotNull(message = "Photographer ID is required")
    private Long photographerId;

    @NotNull(message = "Client requirement is required")
    private ClientRequirementRequest clientRequirement;

    private BookingEquipmentRequirementRequest bookingEquipmentRequirement;
}