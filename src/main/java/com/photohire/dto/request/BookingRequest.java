package com.photohire.dto.request;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class BookingRequest {

    private Long clientId;

    private Long photographerId;

    private String location;

    private LocalDate startDate;

    private LocalDate endDate;

    private Integer totalDays;

    private BigDecimal expectedBudget;
}