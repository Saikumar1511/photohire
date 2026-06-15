package com.photohire.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingRequest {

    private Long clientId;

    private String location;

    private LocalDate startDate;

    private LocalDate endDate;

    private String specialInstructions;
}