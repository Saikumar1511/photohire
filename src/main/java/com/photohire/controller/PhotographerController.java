package com.photohire.controller;

import com.photohire.dto.request.PhotographerProfileRequest;
import com.photohire.dto.response.PhotographerProfileResponse;
import com.photohire.service.PhotographerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/photographers")
public class PhotographerController {

    private final PhotographerService photographerService;

    public PhotographerController(
            PhotographerService photographerService) {

        this.photographerService =
                photographerService;
    }

    @PostMapping
    public ResponseEntity<PhotographerProfileResponse>
    createProfile(
            @RequestBody
            PhotographerProfileRequest request) {

        PhotographerProfileResponse response =
                photographerService.createProfile(
                        request);

        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED);
    }

    @GetMapping("/{profileId}")
    public ResponseEntity<PhotographerProfileResponse>
    getProfileById(
            @PathVariable Long profileId) {

        PhotographerProfileResponse response =
                photographerService.getProfileById(
                        profileId);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<
            List<PhotographerProfileResponse>>
    getAllPhotographers() {

        List<PhotographerProfileResponse>
                photographers =
                photographerService
                        .getAllPhotographers();

        return ResponseEntity.ok(
                photographers);
    }

    @GetMapping("/available")
    public ResponseEntity<
            List<PhotographerProfileResponse>>
    getAvailablePhotographers() {

        List<PhotographerProfileResponse>
                photographers =
                photographerService
                        .getAvailablePhotographers();

        return ResponseEntity.ok(
                photographers);
    }

    @PutMapping("/{profileId}")
    public ResponseEntity<PhotographerProfileResponse>
    updateProfile(
            @PathVariable Long profileId,
            @RequestBody
            PhotographerProfileRequest request) {

        PhotographerProfileResponse response =
                photographerService.updateProfile(
                        profileId,
                        request);

        return ResponseEntity.ok(response);
    }
}