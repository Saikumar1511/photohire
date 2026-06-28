package com.photohire.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

@Data
@Getter
@Setter
public class AvailabilityRequest {

    @NotNull(message = "Available date is required")
    private LocalDate availableDate;

    @NotNull(message = "Photographer profile ID is required")
    private Long photographerProfileId;
}