package com.photohire.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PhotographerProfileRequest {

    private Long userId;

    private String city;

    private String bio;

    private BigDecimal dailyRate;

    private Integer yearsOfExperience;

    private Boolean available;
}