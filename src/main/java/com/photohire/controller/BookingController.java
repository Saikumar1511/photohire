package com.photohire.controller;

import com.photohire.dto.request.BookingRequest;
import com.photohire.dto.response.BookingResponse;
import com.photohire.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(
            BookingService bookingService) {

        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<BookingResponse>
    createBooking(
            @RequestBody BookingRequest request) {

        BookingResponse response =
                bookingService.createBooking(
                        request);

        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingResponse>
    getBookingById(
            @PathVariable Long bookingId) {

        return ResponseEntity.ok(
                bookingService.getBookingById(
                        bookingId));
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<
            List<BookingResponse>>
    getClientBookings(
            @PathVariable Long clientId) {

        return ResponseEntity.ok(
                bookingService.getClientBookings(
                        clientId));
    }

    @GetMapping(
            "/photographer/{photographerId}")
    public ResponseEntity<
            List<BookingResponse>>
    getPhotographerBookings(
            @PathVariable Long photographerId) {

        return ResponseEntity.ok(
                bookingService
                        .getPhotographerBookings(
                                photographerId));
    }

    @PutMapping("/{bookingId}/cancel")
    public ResponseEntity<BookingResponse>
    cancelBooking(
            @PathVariable Long bookingId) {

        return ResponseEntity.ok(
                bookingService.cancelBooking(
                        bookingId));
    }
}