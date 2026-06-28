package com.photohire.service.impl;

import com.photohire.dto.request.AvailabilityRequest;
import com.photohire.dto.response.AvailabilityResponse;
import com.photohire.entity.Availability;
import com.photohire.entity.PhotographerProfile;
import com.photohire.exception.AvailabilityNotFoundException;
import com.photohire.exception.PhotographerNotFoundException;
import com.photohire.mapper.AvailabilityMapper;
import com.photohire.repository.AvailabilityRepository;
import com.photohire.repository.PhotographerProfileRepository;
import com.photohire.service.AvailabilityService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AvailabilityServiceImpl
                implements AvailabilityService {

        private final AvailabilityRepository availabilityRepository;

        private final PhotographerProfileRepository photographerProfileRepository;

        public AvailabilityServiceImpl(
                        AvailabilityRepository availabilityRepository,
                        PhotographerProfileRepository photographerProfileRepository) {

                this.availabilityRepository = availabilityRepository;

                this.photographerProfileRepository = photographerProfileRepository;
        }

        @Override
        public AvailabilityResponse addAvailability(
                        AvailabilityRequest request) {

                PhotographerProfile profile = photographerProfileRepository.findById(
                                request.getPhotographerProfileId())
                                .orElseThrow(() -> new PhotographerNotFoundException(
                                                "Photographer profile not found"));

                Availability availability = new Availability();

                availability.setAvailableDate(
                                request.getAvailableDate());

                availability.setPhotographerProfile(
                                profile);

                Availability savedAvailability = availabilityRepository.save(
                                availability);

                return AvailabilityMapper.toResponse(
                                savedAvailability);
        }

        @Override
        public List<AvailabilityResponse> getPhotographerAvailability(
                        Long photographerProfileId) {

                PhotographerProfile profile = photographerProfileRepository.findById(
                                photographerProfileId)
                                .orElseThrow(() -> new PhotographerNotFoundException(
                                                "Photographer profile not found"));

                return availabilityRepository
                                .findByPhotographerProfile(profile)
                                .stream()
                                .map(AvailabilityMapper::toResponse)
                                .collect(Collectors.toList());
        }

        @Override
        public AvailabilityResponse updateAvailability(
                        Long availabilityId,
                        AvailabilityRequest request) {

                Availability availability = availabilityRepository.findById(
                                availabilityId)
                                .orElseThrow(() -> new AvailabilityNotFoundException(
                                                "Availability not found"));

                PhotographerProfile profile = photographerProfileRepository.findById(
                                request.getPhotographerProfileId())
                                .orElseThrow(() -> new PhotographerNotFoundException(
                                                "Photographer profile not found"));

                availability.setAvailableDate(
                                request.getAvailableDate());

                availability.setPhotographerProfile(
                                profile);

                Availability updatedAvailability = availabilityRepository.save(
                                availability);

                return AvailabilityMapper.toResponse(
                                updatedAvailability);
        }

        @Override
        public void deleteAvailability(
                        Long availabilityId) {

                Availability availability = availabilityRepository.findById(
                                availabilityId)
                                .orElseThrow(() -> new AvailabilityNotFoundException(
                                                "Availability not found"));

                availabilityRepository.delete(
                                availability);
        }
}