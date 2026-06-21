package com.photohire.dto.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AvailabilityResponse {

    private Long id;

    private Long photographerProfileId;

    private LocalDate availableDate;
}