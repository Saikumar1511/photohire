package com.photohire.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AvailabilityRequest {

    private Long photographerProfileId;

    private LocalDate availableDate;
}