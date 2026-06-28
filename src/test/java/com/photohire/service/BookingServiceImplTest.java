package com.photohire.service;

import com.photohire.dto.request.BookingRequest;
import com.photohire.entity.Booking;
import com.photohire.entity.PhotographerProfile;
import com.photohire.entity.User;
import com.photohire.enums.BookingStatus;
import com.photohire.exception.BookingNotFoundException;
import com.photohire.exception.PhotographerNotFoundException;
import com.photohire.exception.PhotographerUnavailableException;
import com.photohire.exception.UserNotFoundException;
import com.photohire.repository.*;
import com.photohire.service.impl.BookingServiceImpl;
import com.photohire.dto.response.BookingResponse;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class BookingServiceImplTest {

        @Mock
        private BookingRepository bookingRepository;

        @Mock
        private UserRepository userRepository;

        @Mock
        private PhotographerProfileRepository photographerProfileRepository;

        @Mock
        private AvailabilityRepository availabilityRepository;

        @InjectMocks
        private BookingServiceImpl bookingService;

        private User client;
        private User photographer;
        private PhotographerProfile profile;
        private Booking booking;
        private BookingRequest request;

        @BeforeEach
        void setUp() {

                MockitoAnnotations.openMocks(this);

                client = new User();
                client.setId(1L);

                photographer = new User();
                photographer.setId(2L);

                profile = new PhotographerProfile();
                profile.setId(1L);
                profile.setUser(photographer);

                request = new BookingRequest();
                request.setClientId(1L);
                request.setPhotographerId(2L);
                request.setLocation("Coorg");
                request.setStartDate(LocalDate.of(2026, 8, 1));
                request.setEndDate(LocalDate.of(2026, 8, 3));
                request.setTotalDays(3);
                request.setExpectedBudget(BigDecimal.valueOf(18000));

                booking = new Booking();
                booking.setId(1L);
                booking.setLocation("Coorg");
                booking.setStartDate(request.getStartDate());
                booking.setEndDate(request.getEndDate());
                booking.setTotalDays(3);
                booking.setExpectedBudget(BigDecimal.valueOf(18000));
                booking.setStatus(BookingStatus.PENDING);
                booking.setClient(client);
                booking.setPhotographer(photographer);
        }

        @Test
        void shouldCreateBookingSuccessfully() {

                when(userRepository.findById(1L))
                                .thenReturn(Optional.of(client));

                when(userRepository.findById(2L))
                                .thenReturn(Optional.of(photographer));

                when(photographerProfileRepository.findByUser(photographer))
                                .thenReturn(Optional.of(profile));

                when(
                                availabilityRepository
                                                .existsByPhotographerProfileAndAvailableDate(
                                                                any(),
                                                                any()))
                                .thenReturn(true);

                when(bookingRepository.save(any(Booking.class)))
                                .thenReturn(booking);

                BookingResponse response = bookingService.createBooking(request);

                assertNotNull(response);
                assertEquals(1L, response.getId());
                assertEquals("Coorg", response.getLocation());

                verify(bookingRepository, times(1))
                                .save(any(Booking.class));
        }

        @Test
        void shouldThrowExceptionWhenClientNotFound() {

                when(userRepository.findById(1L))
                                .thenReturn(Optional.empty());

                assertThrows(
                                UserNotFoundException.class,
                                () -> bookingService.createBooking(request));
        }

        @Test
        void shouldThrowExceptionWhenPhotographerNotFound() {

                when(userRepository.findById(1L))
                                .thenReturn(Optional.of(client));

                when(userRepository.findById(2L))
                                .thenReturn(Optional.empty());

                assertThrows(
                                UserNotFoundException.class,
                                () -> bookingService.createBooking(request));
        }

        @Test
        void shouldThrowExceptionWhenPhotographerProfileNotFound() {

                when(userRepository.findById(1L))
                                .thenReturn(Optional.of(client));

                when(userRepository.findById(2L))
                                .thenReturn(Optional.of(photographer));

                when(photographerProfileRepository.findByUser(photographer))
                                .thenReturn(Optional.empty());

                assertThrows(
                                PhotographerNotFoundException.class,
                                () -> bookingService.createBooking(request));
        }

        @Test
        void shouldThrowExceptionWhenPhotographerUnavailable() {

                when(userRepository.findById(1L))
                                .thenReturn(Optional.of(client));

                when(userRepository.findById(2L))
                                .thenReturn(Optional.of(photographer));

                when(photographerProfileRepository.findByUser(photographer))
                                .thenReturn(Optional.of(profile));

                when(
                                availabilityRepository
                                                .existsByPhotographerProfileAndAvailableDate(
                                                                any(),
                                                                any()))
                                .thenReturn(false);

                assertThrows(
                                PhotographerUnavailableException.class,
                                () -> bookingService.createBooking(request));
        }

        @Test
        void shouldGetBookingById() {

                when(bookingRepository.findById(1L))
                                .thenReturn(Optional.of(booking));

                BookingResponse response = bookingService.getBookingById(1L);

                assertNotNull(response);
                assertEquals(1L, response.getId());
        }

        @Test
        void shouldThrowExceptionWhenBookingNotFound() {

                when(bookingRepository.findById(1L))
                                .thenReturn(Optional.empty());

                assertThrows(
                                BookingNotFoundException.class,
                                () -> bookingService.getBookingById(1L));
        }

        @Test
        void shouldCancelBookingSuccessfully() {

                when(bookingRepository.findById(1L))
                                .thenReturn(Optional.of(booking));

                booking.setStatus(BookingStatus.CANCELLED);

                when(bookingRepository.save(any(Booking.class)))
                                .thenReturn(booking);

                BookingResponse response = bookingService.cancelBooking(1L);

                assertEquals(
                                BookingStatus.CANCELLED,
                                response.getStatus());
        }

        @Test
        void shouldThrowExceptionWhenCancellingInvalidBooking() {

                when(bookingRepository.findById(1L))
                                .thenReturn(Optional.empty());

                assertThrows(
                                BookingNotFoundException.class,
                                () -> bookingService.cancelBooking(1L));
        }
}