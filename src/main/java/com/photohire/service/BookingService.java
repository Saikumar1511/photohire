package com.photohire.service;

import com.photohire.dto.request.BookingRequest;
import com.photohire.dto.response.BookingResponse;

import java.util.List;

public interface BookingService {

    BookingResponse createBooking(
            BookingRequest request);

    BookingResponse getBookingById(
            Long bookingId);

    List<BookingResponse>
    getClientBookings(Long clientId);

    List<BookingResponse>
    getPhotographerBookings(Long photographerId);

    BookingResponse cancelBooking(
            Long bookingId);

            

}