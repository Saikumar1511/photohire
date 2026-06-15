package com.photohire.service.impl;

import com.photohire.dto.request.BookingRequest;
import com.photohire.dto.response.BookingResponse;
import com.photohire.service.BookingService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingServiceImpl
        implements BookingService {

    @Override
    public BookingResponse createBooking(
            BookingRequest request) {

        return null;
    }

    @Override
    public BookingResponse getBookingById(
            Long bookingId) {

        return null;
    }

    @Override
    public List<BookingResponse>
    getClientBookings(Long clientId) {

        return List.of();
    }

    @Override
    public List<BookingResponse>
    getPhotographerBookings(Long photographerId) {

        return List.of();
    }

    @Override
    public BookingResponse cancelBooking(
            Long bookingId) {

        return null;
    }
}