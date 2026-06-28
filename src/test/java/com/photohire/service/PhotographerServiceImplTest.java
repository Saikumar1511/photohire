package com.photohire.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.photohire.dto.request.PhotographerProfileRequest;
import com.photohire.dto.response.PhotographerProfileResponse;
import com.photohire.entity.PhotographerProfile;
import com.photohire.entity.User;
import com.photohire.enums.UserRole;
import com.photohire.exception.PhotographerNotFoundException;
import com.photohire.exception.ResourceAlreadyExistsException;
import com.photohire.exception.UserNotFoundException;
import com.photohire.repository.PhotographerProfileRepository;
import com.photohire.repository.UserRepository;
import com.photohire.service.impl.PhotographerServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class PhotographerServiceImplTest {

        @Mock
        private PhotographerProfileRepository photographerProfileRepository;

        @Mock
        private UserRepository userRepository;

        @InjectMocks
        private PhotographerServiceImpl photographerService;

        private User user;
        private PhotographerProfile profile;
        private PhotographerProfileRequest request;

        @BeforeEach
        void setUp() {

                MockitoAnnotations.openMocks(this);

                user = new User();
                user.setId(1L);
                user.setFirstName("Arjun");
                user.setLastName("Reddy");
                user.setEmail("arjun@gmail.com");
                user.setPhoneNumber("9999999999");
                user.setPassword("password123");
                user.setRole(UserRole.PHOTOGRAPHER);

                request = new PhotographerProfileRequest();
                request.setUserId(1L);
                request.setCity("Bangalore");
                request.setBio("Wedding Photographer");
                request.setDailyRate(BigDecimal.valueOf(5000));
                request.setYearsOfExperience(5);
                request.setAvailable(true);

                profile = new PhotographerProfile();
                profile.setId(1L);
                profile.setCity("Bangalore");
                profile.setBio("Wedding Photographer");
                profile.setDailyRate(BigDecimal.valueOf(5000));
                profile.setYearsOfExperience(5);
                profile.setAvailable(true);
                profile.setUser(user);
        }

        @Test
        void shouldCreatePhotographerProfileSuccessfully() {

                when(userRepository.findById(1L))
                                .thenReturn(Optional.of(user));
                when(photographerProfileRepository.findByUser(user))
                                .thenReturn(Optional.empty());

                when(photographerProfileRepository.save(any(PhotographerProfile.class)))
                                .thenReturn(profile);

                PhotographerProfileResponse response = photographerService.createProfile(request);

                assertNotNull(response);
                assertEquals(1L, response.getId());
                assertEquals("Bangalore", response.getCity());
                assertEquals("Wedding Photographer", response.getBio());

                verify(photographerProfileRepository)
                                .save(any(PhotographerProfile.class));
        }

        @Test
        void shouldThrowExceptionWhenUserNotFound() {

                when(userRepository.findById(1L))
                                .thenReturn(Optional.empty());

                assertThrows(
                                UserNotFoundException.class,
                                () -> photographerService.createProfile(request));

                verify(photographerProfileRepository, never())
                                .save(any());
        }

        @Test
        void shouldGetPhotographerProfileById() {

                when(photographerProfileRepository.findById(1L))
                                .thenReturn(Optional.of(profile));

                PhotographerProfileResponse response = photographerService.getProfileById(1L);

                assertNotNull(response);
                assertEquals(1L, response.getId());
                assertEquals("Bangalore", response.getCity());
        }

        @Test
        void shouldThrowExceptionWhenProfileNotFound() {

                when(photographerProfileRepository.findById(1L))
                                .thenReturn(Optional.empty());

                assertThrows(
                                PhotographerNotFoundException.class,
                                () -> photographerService.getProfileById(1L));
        }

        @Test
        void shouldUpdatePhotographerProfileSuccessfully() {

                when(photographerProfileRepository.findById(1L))
                                .thenReturn(Optional.of(profile));

                when(userRepository.findById(1L))
                                .thenReturn(Optional.of(user));

                when(photographerProfileRepository.save(any(PhotographerProfile.class)))
                                .thenReturn(profile);

                PhotographerProfileResponse response = photographerService.updateProfile(1L, request);

                assertNotNull(response);
                assertEquals("Bangalore", response.getCity());
                assertEquals("Wedding Photographer", response.getBio());

                verify(photographerProfileRepository)
                                .save(any(PhotographerProfile.class));
        }

        @Test
        void shouldThrowExceptionWhenUpdatingInvalidProfile() {

                when(photographerProfileRepository.findById(1L))
                                .thenReturn(Optional.empty());

                assertThrows(
                                PhotographerNotFoundException.class,
                                () -> photographerService.updateProfile(1L, request));

                verify(photographerProfileRepository, never())
                                .save(any());
        }

        @Test
        void shouldThrowExceptionWhenUpdatingWithInvalidUser() {

                when(photographerProfileRepository.findById(1L))
                                .thenReturn(Optional.of(profile));

                when(userRepository.findById(1L))
                                .thenReturn(Optional.empty());

                assertThrows(
                                UserNotFoundException.class,
                                () -> photographerService.updateProfile(1L, request));

                verify(photographerProfileRepository, never())
                                .save(any());
        }

        @Test
        void shouldReturnAllPhotographers() {

                PhotographerProfile profile2 = new PhotographerProfile();

                profile2.setId(2L);
                profile2.setCity("Hyderabad");
                profile2.setBio("Fashion Photographer");
                profile2.setDailyRate(BigDecimal.valueOf(7000));
                profile2.setYearsOfExperience(8);
                profile2.setAvailable(true);

                when(photographerProfileRepository.findAll())
                                .thenReturn(List.of(profile, profile2));

                List<PhotographerProfileResponse> response = photographerService.getAllPhotographers();

                assertEquals(2, response.size());
                assertEquals("Bangalore",
                                response.get(0).getCity());
                assertEquals("Hyderabad",
                                response.get(1).getCity());

                verify(photographerProfileRepository)
                                .findAll();
        }

        @Test
        void shouldReturnAvailablePhotographers() {

                when(photographerProfileRepository.findByAvailableTrue())
                                .thenReturn(List.of(profile));

                List<PhotographerProfileResponse> response = photographerService.getAvailablePhotographers();

                assertEquals(1, response.size());
                assertTrue(response.get(0).getAvailable());

                verify(photographerProfileRepository)
                                .findByAvailableTrue();
        }

        @Test
        void shouldDeletePhotographerProfileSuccessfully() {

                when(photographerProfileRepository.findById(1L))
                                .thenReturn(Optional.of(profile));

                photographerService.deleteProfile(1L);

                verify(photographerProfileRepository)
                                .delete(profile);
        }

        @Test
        void shouldThrowExceptionWhenDeletingInvalidProfile() {

                when(photographerProfileRepository.findById(1L))
                                .thenReturn(Optional.empty());

                assertThrows(
                                PhotographerNotFoundException.class,
                                () -> photographerService.deleteProfile(1L));

                verify(photographerProfileRepository, never())
                                .delete(any());
        }

        @Test
        void shouldThrowExceptionWhenProfileAlreadyExists() {

                when(userRepository.findById(1L))
                                .thenReturn(Optional.of(user));

                when(photographerProfileRepository.findByUser(user))
                                .thenReturn(Optional.of(profile));

                assertThrows(
                                ResourceAlreadyExistsException.class,
                                () -> photographerService.createProfile(request));

                verify(photographerProfileRepository, never())
                                .save(any());
        }
}