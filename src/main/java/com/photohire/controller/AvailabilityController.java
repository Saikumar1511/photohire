package com.photohire.controller;

import com.photohire.dto.request.AvailabilityRequest;
import com.photohire.dto.response.AvailabilityResponse;
import com.photohire.service.AvailabilityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/availabilities")
public class AvailabilityController {

    private final AvailabilityService
            availabilityService;

    public AvailabilityController(
            AvailabilityService availabilityService) {

        this.availabilityService =
                availabilityService;
    }

    @PostMapping
    public ResponseEntity<AvailabilityResponse>
    addAvailability(
            @RequestBody
            AvailabilityRequest request) {

        return new ResponseEntity<>(
                availabilityService
                        .addAvailability(request),
                HttpStatus.CREATED);
    }

    @GetMapping("/{photographerProfileId}")
    public ResponseEntity<
            List<AvailabilityResponse>>
    getAvailability(
            @PathVariable
            Long photographerProfileId) {

        return ResponseEntity.ok(
                availabilityService
                        .getPhotographerAvailability(
                                photographerProfileId));
    }

    @DeleteMapping("/{availabilityId}")
    public ResponseEntity<Void>
    deleteAvailability(
            @PathVariable Long availabilityId) {

        availabilityService.deleteAvailability(
                availabilityId);

        return ResponseEntity.noContent()
                .build();
    }
}