package com.photohire.mapper;

import com.photohire.dto.response.AvailabilityResponse;
import com.photohire.entity.Availability;

public class AvailabilityMapper {

        private AvailabilityMapper() {
        }

        public static AvailabilityResponse toResponse(
                        Availability availability) {

                AvailabilityResponse response = new AvailabilityResponse();

                response.setId(
                                availability.getId());

                response.setAvailableDate(
                                availability.getAvailableDate());

                if (availability.getPhotographerProfile() != null) {
                        response.setPhotographerProfileId(
                                        availability.getPhotographerProfile().getId());
                }

                return response;
        }
}