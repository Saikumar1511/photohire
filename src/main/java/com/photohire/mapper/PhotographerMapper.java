package com.photohire.mapper;

import com.photohire.dto.request.PhotographerProfileRequest;
import com.photohire.dto.response.PhotographerProfileResponse;
import com.photohire.entity.PhotographerProfile;

public class PhotographerMapper {

    private PhotographerMapper() {
    }

    public static PhotographerProfile toEntity(
            PhotographerProfileRequest request) {

        PhotographerProfile profile =
                new PhotographerProfile();

        profile.setCity(request.getCity());
        profile.setBio(request.getBio());
        profile.setDailyRate(request.getDailyRate());
        profile.setYearsOfExperience(
                request.getYearsOfExperience());
        profile.setAvailable(
                request.getAvailable());

        return profile;
    }

    public static PhotographerProfileResponse toResponse(
            PhotographerProfile profile) {

        PhotographerProfileResponse response =
                new PhotographerProfileResponse();

        response.setId(profile.getId());
        response.setCity(profile.getCity());
        response.setBio(profile.getBio());
        response.setDailyRate(profile.getDailyRate());
        response.setYearsOfExperience(
                profile.getYearsOfExperience());
        response.setAvailable(
                profile.getAvailable());

        return response;
    }
}