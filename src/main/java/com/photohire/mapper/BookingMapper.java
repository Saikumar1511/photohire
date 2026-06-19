package com.photohire.mapper;

import com.photohire.dto.request.BookingRequest;
import com.photohire.dto.response.BookingResponse;
import com.photohire.entity.Booking;

public class BookingMapper {

    private BookingMapper() {
    }

    public static Booking toEntity(
            BookingRequest request) {

        Booking booking = new Booking();

        booking.setLocation(
                request.getLocation());

        booking.setStartDate(
                request.getStartDate());

        booking.setEndDate(
                request.getEndDate());
        
        booking.setTotalDays(
                request.getTotalDays());

        booking.setExpectedBudget(
                request.getExpectedBudget());


        return booking;
    }

    public static BookingResponse toResponse(
            Booking booking) {

        BookingResponse response =
                new BookingResponse();

        response.setId(
                booking.getId());

        response.setLocation(
                booking.getLocation());

        response.setStartDate(
                booking.getStartDate());

        response.setEndDate(
                booking.getEndDate());

        response.setStatus(
                booking.getStatus());

        response.setTotalDays(
                booking.getTotalDays());

        response.setExpectedBudget(
                booking.getExpectedBudget());

        if (booking.getClient() != null) {

            response.setClientId(
                    booking.getClient().getId());
        }

        if (booking.getPhotographer() != null) {

            response.setPhotographerId(
                    booking.getPhotographer().getId());
        }

        return response;
    }
}