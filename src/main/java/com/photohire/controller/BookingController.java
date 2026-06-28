package com.photohire.controller;

import com.photohire.dto.request.BookingRequest;
import com.photohire.dto.response.BookingResponse;
import com.photohire.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/bookings")
public class BookingController {

        private final BookingService bookingService;

        public BookingController(
                        BookingService bookingService) {

                this.bookingService = bookingService;
        }

        @PostMapping
        public ResponseEntity<BookingResponse> createBooking(
                        @Valid @RequestBody BookingRequest request) {

                BookingResponse response = bookingService.createBooking(request);

                return new ResponseEntity<>(
                                response,
                                HttpStatus.CREATED);
        }

        @GetMapping("/{bookingId}")
        public ResponseEntity<BookingResponse> getBookingById(
                        @PathVariable Long bookingId) {

                BookingResponse response = bookingService.getBookingById(bookingId);

                return ResponseEntity.ok(response);
        }

        @GetMapping("/client/{clientId}")
        public ResponseEntity<List<BookingResponse>> getClientBookings(
                        @PathVariable Long clientId) {

                List<BookingResponse> response = bookingService.getClientBookings(clientId);

                return ResponseEntity.ok(response);
        }

        @GetMapping("/photographer/{photographerId}")
        public ResponseEntity<List<BookingResponse>> getPhotographerBookings(
                        @PathVariable Long photographerId) {

                List<BookingResponse> response = bookingService.getPhotographerBookings(photographerId);

                return ResponseEntity.ok(response);
        }

        @PutMapping("/{bookingId}")
        public ResponseEntity<BookingResponse> updateBooking(
                        @PathVariable Long bookingId,
                        @Valid @RequestBody BookingRequest request) {

                BookingResponse response = bookingService.updateBooking(
                                bookingId,
                                request);

                return ResponseEntity.ok(response);
        }

        @PutMapping("/{bookingId}/cancel")
        public ResponseEntity<BookingResponse> cancelBooking(
                        @PathVariable Long bookingId) {

                BookingResponse response = bookingService.cancelBooking(bookingId);

                return ResponseEntity.ok(response);
        }
}