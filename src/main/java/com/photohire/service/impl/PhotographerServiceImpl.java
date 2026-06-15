package com.photohire.service.impl;

import com.photohire.dto.request.PhotographerProfileRequest;
import com.photohire.dto.response.PhotographerProfileResponse;
import com.photohire.service.PhotographerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhotographerServiceImpl
        implements PhotographerService {

    @Override
    public PhotographerProfileResponse createProfile(
            PhotographerProfileRequest request) {

        return null;
    }

    @Override
    public PhotographerProfileResponse getProfileById(
            Long profileId) {

        return null;
    }

    @Override
    public PhotographerProfileResponse updateProfile(
            Long profileId,
            PhotographerProfileRequest request) {

        return null;
    }

    @Override
    public List<PhotographerProfileResponse>
    getAllPhotographers() {

        return List.of();
    }

    @Override
    public List<PhotographerProfileResponse>
    getAvailablePhotographers() {

        return List.of();
    }
}