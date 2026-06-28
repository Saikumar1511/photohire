package com.photohire.controller;

import com.photohire.dto.request.AvailabilityRequest;
import com.photohire.dto.response.AvailabilityResponse;
import com.photohire.service.AvailabilityService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/availabilities")
public class AvailabilityController {

        private final AvailabilityService availabilityService;

        public AvailabilityController(
                        AvailabilityService availabilityService) {

                this.availabilityService = availabilityService;
        }

        @PostMapping
        public ResponseEntity<AvailabilityResponse> addAvailability(
                        @Valid @RequestBody AvailabilityRequest request) {

                AvailabilityResponse response = availabilityService.addAvailability(request);

                return new ResponseEntity<>(
                                response,
                                HttpStatus.CREATED);
        }

        @GetMapping("/{photographerProfileId}")
        public ResponseEntity<List<AvailabilityResponse>> getAvailability(
                        @PathVariable Long photographerProfileId) {

                List<AvailabilityResponse> response = availabilityService.getPhotographerAvailability(
                                photographerProfileId);

                return ResponseEntity.ok(response);
        }

        @PutMapping("/{availabilityId}")
        public ResponseEntity<AvailabilityResponse> updateAvailability(
                        @PathVariable Long availabilityId,
                        @Valid @RequestBody AvailabilityRequest request) {

                AvailabilityResponse response = availabilityService.updateAvailability(
                                availabilityId,
                                request);

                return ResponseEntity.ok(response);
        }

        @DeleteMapping("/{availabilityId}")
        public ResponseEntity<Void> deleteAvailability(
                        @PathVariable Long availabilityId) {

                availabilityService.deleteAvailability(
                                availabilityId);

                return ResponseEntity.noContent().build();
        }
}