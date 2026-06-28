package com.photohire.service.impl;

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
import com.photohire.mapper.BookingMapper;
import com.photohire.repository.AvailabilityRepository;
import com.photohire.repository.BookingRepository;
import com.photohire.repository.PhotographerProfileRepository;
import com.photohire.repository.UserRepository;
import com.photohire.service.BookingService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

        private final BookingRepository bookingRepository;
        private final UserRepository userRepository;
        private final PhotographerProfileRepository photographerProfileRepository;
        private final AvailabilityRepository availabilityRepository;

        public BookingServiceImpl(
                        BookingRepository bookingRepository,
                        UserRepository userRepository,
                        PhotographerProfileRepository photographerProfileRepository,
                        AvailabilityRepository availabilityRepository) {

                this.bookingRepository = bookingRepository;
                this.userRepository = userRepository;
                this.photographerProfileRepository = photographerProfileRepository;
                this.availabilityRepository = availabilityRepository;
        }

        @Override
        public BookingResponse createBooking(
                        BookingRequest request) {

                User client = userRepository.findById(
                                request.getClientId())
                                .orElseThrow(() -> new UserNotFoundException(
                                                "Client not found with id "
                                                                + request.getClientId()));

                User photographer = userRepository.findById(
                                request.getPhotographerId())
                                .orElseThrow(() -> new UserNotFoundException(
                                                "Photographer not found with id "
                                                                + request.getPhotographerId()));

                PhotographerProfile profile = photographerProfileRepository
                                .findByUser(photographer)
                                .orElseThrow(() -> new PhotographerNotFoundException(
                                                "Photographer profile not found"));

                for (LocalDate date = request.getStartDate(); !date.isAfter(request.getEndDate()); date = date
                                .plusDays(1)) {

                        boolean available = availabilityRepository
                                        .existsByPhotographerProfileAndAvailableDate(
                                                        profile,
                                                        date);

                        if (!available) {
                                throw new PhotographerUnavailableException(
                                                "Photographer unavailable on " + date);
                        }
                }

                Booking booking = BookingMapper.toEntity(request);

                booking.setClient(client);
                booking.setPhotographer(photographer);
                booking.setStatus(BookingStatus.PENDING);

                Booking savedBooking = bookingRepository.save(booking);

                return BookingMapper.toResponse(savedBooking);
        }

        @Override
        public BookingResponse getBookingById(
                        Long bookingId) {

                Booking booking = bookingRepository.findById(bookingId)
                                .orElseThrow(() -> new BookingNotFoundException(
                                                "Booking not found with id "
                                                                + bookingId));

                return BookingMapper.toResponse(booking);
        }

        @Override
        public List<BookingResponse> getClientBookings(
                        Long clientId) {

                User client = userRepository.findById(clientId)
                                .orElseThrow(() -> new UserNotFoundException(
                                                "Client not found"));

                return bookingRepository.findByClient(client)
                                .stream()
                                .map(BookingMapper::toResponse)
                                .collect(Collectors.toList());
        }

        @Override
        public List<BookingResponse> getPhotographerBookings(
                        Long photographerId) {

                User photographer = userRepository.findById(photographerId)
                                .orElseThrow(() -> new UserNotFoundException(
                                                "Photographer not found"));

                return bookingRepository
                                .findByPhotographer(photographer)
                                .stream()
                                .map(BookingMapper::toResponse)
                                .collect(Collectors.toList());
        }

        @Override
        public BookingResponse cancelBooking(
                        Long bookingId) {

                Booking booking = bookingRepository.findById(bookingId)
                                .orElseThrow(() -> new BookingNotFoundException(
                                                "Booking not found with id "
                                                                + bookingId));
                booking.setStatus(
                                BookingStatus.CANCELLED);
                Booking updatedBooking = bookingRepository.save(booking);
                return BookingMapper.toResponse(updatedBooking);
        }

        @Override
        public BookingResponse updateBooking(
                        Long bookingId,
                        BookingRequest request) {
                Booking booking = bookingRepository.findById(bookingId)
                                .orElseThrow(() -> new BookingNotFoundException(
                                                "Booking not found with id "
                                                                + bookingId));

                User client = userRepository.findById(request.getClientId())
                                .orElseThrow(() -> new UserNotFoundException(
                                                "Client not found with id "
                                                                + request.getClientId()));

                User photographer = userRepository.findById(request.getPhotographerId())
                                .orElseThrow(() -> new UserNotFoundException(
                                                "Photographer not found with id "
                                                                + request.getPhotographerId()));

                PhotographerProfile profile = photographerProfileRepository
                                .findByUser(photographer)
                                .orElseThrow(() -> new PhotographerNotFoundException(
                                                "Photographer profile not found"));

                // Validate photographer availability
                for (LocalDate date = request.getStartDate(); !date.isAfter(request.getEndDate()); date = date
                                .plusDays(1)) {

                        boolean available = availabilityRepository
                                        .existsByPhotographerProfileAndAvailableDate(
                                                        profile,
                                                        date);

                        if (!available) {
                                throw new PhotographerUnavailableException(
                                                "Photographer unavailable on " + date);
                        }
                }
                booking.setLocation(request.getLocation());
                booking.setStartDate(request.getStartDate());
                booking.setEndDate(request.getEndDate());
                booking.setTotalDays(request.getTotalDays());
                booking.setExpectedBudget(request.getExpectedBudget());
                booking.setClient(client);
                booking.setPhotographer(photographer);
                Booking updatedBooking = bookingRepository.save(booking);
                return BookingMapper.toResponse(updatedBooking);
        }
}