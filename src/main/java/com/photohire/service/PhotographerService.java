package com.photohire.service;

import com.photohire.dto.request.PhotographerProfileRequest;
import com.photohire.dto.response.PhotographerProfileResponse;

import java.util.List;

public interface PhotographerService {

        PhotographerProfileResponse createProfile(
                        PhotographerProfileRequest request);

        PhotographerProfileResponse getProfileById(
                        Long profileId);

        PhotographerProfileResponse updateProfile(
                        Long profileId,
                        PhotographerProfileRequest request);

        List<PhotographerProfileResponse> getAllPhotographers();

        List<PhotographerProfileResponse> getAvailablePhotographers();

        void deleteProfile(
                        Long profileId);
}