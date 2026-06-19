package com.photohire.service.impl;

import com.photohire.dto.request.PhotographerProfileRequest;
import com.photohire.dto.response.PhotographerProfileResponse;
import com.photohire.entity.PhotographerProfile;
import com.photohire.entity.User;
import com.photohire.exception.PhotographerNotFoundException;
import com.photohire.exception.UserNotFoundException;
import com.photohire.mapper.PhotographerMapper;
import com.photohire.repository.PhotographerProfileRepository;
import com.photohire.repository.UserRepository;
import com.photohire.service.PhotographerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PhotographerServiceImpl
        implements PhotographerService {

    private final PhotographerProfileRepository photographerProfileRepository;
    private final UserRepository userRepository;

    public PhotographerServiceImpl(
            PhotographerProfileRepository photographerProfileRepository,
            UserRepository userRepository) {

        this.photographerProfileRepository =
                photographerProfileRepository;

        this.userRepository =
                userRepository;
    }

    @Override
    public PhotographerProfileResponse createProfile(
            PhotographerProfileRequest request) {

        User user = userRepository.findById(
                        request.getUserId())
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with id "
                                        + request.getUserId()));

        PhotographerProfile profile =
                PhotographerMapper.toEntity(request);

        profile.setUser(user);

        PhotographerProfile savedProfile =
                photographerProfileRepository.save(profile);

        return PhotographerMapper.toResponse(
                savedProfile);
    }

    @Override
    public PhotographerProfileResponse getProfileById(
            Long profileId) {

        PhotographerProfile profile =
                photographerProfileRepository.findById(
                                profileId)
                        .orElseThrow(() ->
                                new PhotographerNotFoundException(
                                        "Photographer profile not found with id "
                                                + profileId));

        return PhotographerMapper.toResponse(
                profile);
    }

    @Override
    public PhotographerProfileResponse updateProfile(
            Long profileId,
            PhotographerProfileRequest request) {

        PhotographerProfile profile =
                photographerProfileRepository.findById(
                                profileId)
                        .orElseThrow(() ->
                                new PhotographerNotFoundException(
                                        "Photographer profile not found with id "
                                                + profileId));

        profile.setCity(request.getCity());
        profile.setBio(request.getBio());
        profile.setDailyRate(request.getDailyRate());
        profile.setYearsOfExperience(
                request.getYearsOfExperience());
        profile.setAvailable(
                request.getAvailable());

        PhotographerProfile updatedProfile =
                photographerProfileRepository.save(
                        profile);

        return PhotographerMapper.toResponse(
                updatedProfile);
    }

    @Override
    public List<PhotographerProfileResponse>
    getAllPhotographers() {

        return photographerProfileRepository.findAll()
                .stream()
                .map(PhotographerMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<PhotographerProfileResponse>
    getAvailablePhotographers() {

        return photographerProfileRepository
                .findByAvailableTrue()
                .stream()
                .map(PhotographerMapper::toResponse)
                .collect(Collectors.toList());
    }
}