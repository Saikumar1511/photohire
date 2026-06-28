package com.photohire.controller;

import com.photohire.dto.request.PhotographerProfileRequest;
import com.photohire.dto.response.PhotographerProfileResponse;
import com.photohire.service.PhotographerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/photographers")
public class PhotographerController {

        private final PhotographerService photographerService;

        public PhotographerController(
                        PhotographerService photographerService) {

                this.photographerService = photographerService;
        }

        @PostMapping
        public ResponseEntity<PhotographerProfileResponse> createProfile(
                        @Valid @RequestBody PhotographerProfileRequest request) {

                PhotographerProfileResponse response = photographerService.createProfile(request);

                return new ResponseEntity<>(
                                response,
                                HttpStatus.CREATED);
        }

        @GetMapping("/{profileId}")
        public ResponseEntity<PhotographerProfileResponse> getProfileById(
                        @PathVariable Long profileId) {

                PhotographerProfileResponse response = photographerService.getProfileById(profileId);

                return ResponseEntity.ok(response);
        }

        @GetMapping
        public ResponseEntity<List<PhotographerProfileResponse>> getAllPhotographers() {

                List<PhotographerProfileResponse> response = photographerService.getAllPhotographers();

                return ResponseEntity.ok(response);
        }

        @GetMapping("/available")
        public ResponseEntity<List<PhotographerProfileResponse>> getAvailablePhotographers() {

                List<PhotographerProfileResponse> response = photographerService.getAvailablePhotographers();

                return ResponseEntity.ok(response);
        }

        @PutMapping("/{profileId}")
        public ResponseEntity<PhotographerProfileResponse> updateProfile(
                        @PathVariable Long profileId,
                        @Valid @RequestBody PhotographerProfileRequest request) {

                PhotographerProfileResponse response = photographerService.updateProfile(
                                profileId,
                                request);

                return ResponseEntity.ok(response);
        }

        @DeleteMapping("/{profileId}")
        public ResponseEntity<Void> deleteProfile(
                        @PathVariable Long profileId) {

                photographerService.deleteProfile(profileId);

                return ResponseEntity.noContent().build();
        }
}