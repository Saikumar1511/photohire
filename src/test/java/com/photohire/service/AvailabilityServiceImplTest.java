package com.photohire.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.photohire.dto.request.AvailabilityRequest;
import com.photohire.dto.response.AvailabilityResponse;
import com.photohire.entity.Availability;
import com.photohire.entity.PhotographerProfile;
import com.photohire.exception.AvailabilityNotFoundException;
import com.photohire.exception.PhotographerNotFoundException;
import com.photohire.repository.AvailabilityRepository;
import com.photohire.repository.PhotographerProfileRepository;
import com.photohire.service.impl.AvailabilityServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AvailabilityServiceImplTest {

    @Mock
    private AvailabilityRepository availabilityRepository;

    @Mock
    private PhotographerProfileRepository photographerProfileRepository;

    @InjectMocks
    private AvailabilityServiceImpl availabilityService;

    private PhotographerProfile profile;
    private Availability availability;
    private AvailabilityRequest request;

    @BeforeEach
    void setUp() {
        profile = new PhotographerProfile();
        profile.setId(1L);

        request = new AvailabilityRequest();
        request.setPhotographerProfileId(1L);
        request.setAvailableDate(LocalDate.of(2026, 8, 1));

        availability = new Availability();
        availability.setId(1L);
        availability.setAvailableDate(LocalDate.of(2026, 8, 1));
        availability.setPhotographerProfile(profile);
    }

    @Test
    void shouldAddAvailabilitySuccessfully() {

        when(photographerProfileRepository.findById(1L))
                .thenReturn(Optional.of(profile));

        when(availabilityRepository.save(any(Availability.class)))
                .thenReturn(availability);

        AvailabilityResponse response = availabilityService.addAvailability(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(
                LocalDate.of(2026, 8, 1),
                response.getAvailableDate());

        verify(availabilityRepository, times(1))
                .save(any(Availability.class));
    }

    @Test
    void shouldThrowExceptionWhenPhotographerProfileNotFoundWhileAdding() {

        when(photographerProfileRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                PhotographerNotFoundException.class,
                () -> availabilityService.addAvailability(request));

        verify(availabilityRepository, never())
                .save(any());
    }

    @Test
    void shouldReturnPhotographerAvailability() {

        when(photographerProfileRepository.findById(1L))
                .thenReturn(Optional.of(profile));

        when(availabilityRepository.findByPhotographerProfile(profile))
                .thenReturn(List.of(availability));

        List<AvailabilityResponse> response = availabilityService.getPhotographerAvailability(1L);

        assertEquals(1, response.size());

        assertEquals(
                LocalDate.of(2026, 8, 1),
                response.get(0).getAvailableDate());

        verify(availabilityRepository)
                .findByPhotographerProfile(profile);
    }

    @Test
    void shouldThrowExceptionWhenGettingAvailabilityForInvalidProfile() {

        when(photographerProfileRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                PhotographerNotFoundException.class,
                () -> availabilityService.getPhotographerAvailability(1L));
    }

    @Test
    void shouldUpdateAvailabilitySuccessfully() {

        when(availabilityRepository.findById(1L))
                .thenReturn(Optional.of(availability));

        when(photographerProfileRepository.findById(1L))
                .thenReturn(Optional.of(profile));

        when(availabilityRepository.save(any(Availability.class)))
                .thenReturn(availability);

        AvailabilityResponse response = availabilityService.updateAvailability(
                1L,
                request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(
                LocalDate.of(2026, 8, 1),
                response.getAvailableDate());

        verify(availabilityRepository)
                .save(any(Availability.class));
    }

    @Test
    void shouldThrowExceptionWhenUpdatingInvalidAvailability() {

        when(availabilityRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                AvailabilityNotFoundException.class,
                () -> availabilityService.updateAvailability(
                        1L,
                        request));

        verify(availabilityRepository, never())
                .save(any());
    }

    @Test
    void shouldThrowExceptionWhenUpdatingAvailabilityWithInvalidPhotographer() {

        when(availabilityRepository.findById(1L))
                .thenReturn(Optional.of(availability));

        when(photographerProfileRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                PhotographerNotFoundException.class,
                () -> availabilityService.updateAvailability(
                        1L,
                        request));

        verify(availabilityRepository, never())
                .save(any());
    }

    @Test
    void shouldDeleteAvailabilitySuccessfully() {

        when(availabilityRepository.findById(1L))
                .thenReturn(Optional.of(availability));

        doNothing().when(availabilityRepository)
                .delete(availability);

        availabilityService.deleteAvailability(1L);

        verify(availabilityRepository, times(1))
                .delete(availability);
    }

    @Test
    void shouldThrowExceptionWhenDeletingInvalidAvailability() {

        when(availabilityRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                AvailabilityNotFoundException.class,
                () -> availabilityService.deleteAvailability(1L));

        verify(availabilityRepository, never())
                .delete(any());
    }
}