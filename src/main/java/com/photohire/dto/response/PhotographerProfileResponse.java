package com.photohire.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PhotographerProfileResponse {

    private Long id;

    private String city;

    private String bio;

    private BigDecimal dailyRate;

    private Integer yearsOfExperience;

    private Boolean available;

    private UserResponse user;
}