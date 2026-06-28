package com.photohire.service;

import com.photohire.dto.request.AvailabilityRequest;
import com.photohire.dto.response.AvailabilityResponse;

import java.util.List;

public interface AvailabilityService {

        AvailabilityResponse addAvailability(
                        AvailabilityRequest request);

        List<AvailabilityResponse> getPhotographerAvailability(
                        Long photographerProfileId);

        AvailabilityResponse updateAvailability(
                        Long availabilityId,
                        AvailabilityRequest request);

        void deleteAvailability(
                        Long availabilityId);
}