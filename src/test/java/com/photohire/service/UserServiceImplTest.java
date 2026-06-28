package com.photohire.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import com.photohire.dto.request.BookingRequest;
import com.photohire.dto.response.BookingResponse;
import com.photohire.entity.Booking;
import com.photohire.entity.PhotographerProfile;
import com.photohire.entity.User;
import com.photohire.enums.BookingStatus;
import com.photohire.exception.BookingNotFoundException;
import com.photohire.exception.PhotographerNotFoundException;
import com.photohire.exception.PhotographerUnavailableException;
import com.photohire.exception.UserNotFoundException;
import com.photohire.repository.AvailabilityRepository;
import com.photohire.repository.BookingRepository;
import com.photohire.repository.PhotographerProfileRepository;
import com.photohire.repository.UserRepository;
import com.photohire.service.impl.BookingServiceImpl;
import com.photohire.service.impl.UserServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class UserServiceImplTest {

        private User client;
        private User photographer;
        private PhotographerProfile profile;
        private Booking booking;
        private BookingRequest request;

        @Mock
        private UserRepository userRepository;

        @Mock
        private BookingRepository bookingRepository;

        @InjectMocks
        private UserServiceImpl userService;

        @InjectMocks
        private BookingServiceImpl bookingService;

        @Mock
        private AvailabilityRepository availabilityRepository;

        @Mock
        private PhotographerProfileRepository photographerProfileRepository;

        @BeforeEach
        void setUp() {

                MockitoAnnotations.openMocks(this);

                client = new User();
                client.setId(1L);
                client.setFirstName("Sai");

                photographer = new User();
                photographer.setId(2L);
                photographer.setFirstName("Arjun");

                profile = new PhotographerProfile();
                profile.setId(1L);
                profile.setUser(photographer);

                request = new BookingRequest();
                request.setClientId(1L);
                request.setPhotographerId(2L);
                request.setLocation("Coorg");
                request.setStartDate(
                                LocalDate.of(2026, 8, 1));
                request.setEndDate(
                                LocalDate.of(2026, 8, 3));
                request.setTotalDays(3);
                request.setExpectedBudget(
                                BigDecimal.valueOf(18000));

                booking = new Booking();
                booking.setId(1L);
                booking.setLocation("Coorg");
                booking.setStartDate(
                                request.getStartDate());
                booking.setEndDate(
                                request.getEndDate());
                booking.setTotalDays(3);
                booking.setExpectedBudget(
                                BigDecimal.valueOf(18000));
                booking.setStatus(
                                BookingStatus.PENDING);
                booking.setClient(client);
                booking.setPhotographer(photographer);
        }

        @Test
        void shouldCancelBookingSuccessfully() {

                when(bookingRepository.findById(1L))
                                .thenReturn(Optional.of(booking));

                booking.setStatus(
                                BookingStatus.CANCELLED);

                when(bookingRepository.save(any(Booking.class)))
                                .thenReturn(booking);

                BookingResponse response = bookingService.cancelBooking(1L);

                assertNotNull(response);

                assertEquals(
                                BookingStatus.CANCELLED,
                                response.getStatus());

                verify(bookingRepository)
                                .save(any(Booking.class));
        }

        @Test
        void shouldThrowExceptionWhenCancellingInvalidBooking() {

                when(bookingRepository.findById(1L))
                                .thenReturn(Optional.empty());

                assertThrows(
                                BookingNotFoundException.class,
                                () -> bookingService.cancelBooking(
                                                1L));

                verify(bookingRepository, never())
                                .save(any());
        }

        @Test
        void shouldUpdateBookingSuccessfully() {

                when(bookingRepository.findById(1L))
                                .thenReturn(Optional.of(booking));

                when(userRepository.findById(1L))
                                .thenReturn(Optional.of(client));

                when(userRepository.findById(2L))
                                .thenReturn(Optional.of(photographer));

                when(photographerProfileRepository.findByUser(
                                photographer))
                                .thenReturn(Optional.of(profile));

                when(availabilityRepository
                                .existsByPhotographerProfileAndAvailableDate(
                                                any(),
                                                any()))
                                .thenReturn(true);

                when(bookingRepository.save(any(Booking.class)))
                                .thenReturn(booking);

                BookingResponse response = bookingService.updateBooking(
                                1L,
                                request);

                assertNotNull(response);

                assertEquals(
                                "Coorg",
                                response.getLocation());

                assertEquals(
                                BookingStatus.PENDING,
                                booking.getStatus());

                verify(bookingRepository)
                                .save(any(Booking.class));
        }

        @Test
        void shouldThrowExceptionWhenUpdatingInvalidBooking() {

                when(bookingRepository.findById(1L))
                                .thenReturn(Optional.empty());

                assertThrows(
                                BookingNotFoundException.class,
                                () -> bookingService.updateBooking(
                                                1L,
                                                request));

                verify(bookingRepository, never())
                                .save(any());
        }

        @Test
        void shouldThrowExceptionWhenUpdatingClientNotFound() {

                when(bookingRepository.findById(1L))
                                .thenReturn(Optional.of(booking));

                when(userRepository.findById(1L))
                                .thenReturn(Optional.empty());

                assertThrows(
                                UserNotFoundException.class,
                                () -> bookingService.updateBooking(
                                                1L,
                                                request));

                verify(bookingRepository, never())
                                .save(any());
        }

        @Test
        void shouldThrowExceptionWhenUpdatingPhotographerNotFound() {

                when(bookingRepository.findById(1L))
                                .thenReturn(Optional.of(booking));

                when(userRepository.findById(1L))
                                .thenReturn(Optional.of(client));

                when(userRepository.findById(2L))
                                .thenReturn(Optional.empty());

                assertThrows(
                                UserNotFoundException.class,
                                () -> bookingService.updateBooking(
                                                1L,
                                                request));

                verify(bookingRepository, never())
                                .save(any());
        }

        @Test
        void shouldThrowExceptionWhenUpdatingPhotographerProfileNotFound() {

                when(bookingRepository.findById(1L))
                                .thenReturn(Optional.of(booking));

                when(userRepository.findById(1L))
                                .thenReturn(Optional.of(client));

                when(userRepository.findById(2L))
                                .thenReturn(Optional.of(photographer));

                when(photographerProfileRepository.findByUser(
                                photographer))
                                .thenReturn(Optional.empty());

                assertThrows(
                                PhotographerNotFoundException.class,
                                () -> bookingService.updateBooking(
                                                1L,
                                                request));

                verify(bookingRepository, never())
                                .save(any());
        }

        @Test
        void shouldThrowExceptionWhenUpdatingBookingForUnavailablePhotographer() {

                when(bookingRepository.findById(1L))
                                .thenReturn(Optional.of(booking));

                when(userRepository.findById(1L))
                                .thenReturn(Optional.of(client));

                when(userRepository.findById(2L))
                                .thenReturn(Optional.of(photographer));

                when(photographerProfileRepository.findByUser(
                                photographer))
                                .thenReturn(Optional.of(profile));

                when(availabilityRepository
                                .existsByPhotographerProfileAndAvailableDate(
                                                any(),
                                                any()))
                                .thenReturn(false);

                assertThrows(
                                PhotographerUnavailableException.class,
                                () -> bookingService.updateBooking(
                                                1L,
                                                request));

                verify(bookingRepository, never())
                                .save(any());
        }
}
