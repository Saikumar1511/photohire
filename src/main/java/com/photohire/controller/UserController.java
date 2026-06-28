package com.photohire.controller;

import com.photohire.dto.request.UserRegistrationRequest;
import com.photohire.dto.response.UserResponse;
import com.photohire.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/users")
public class UserController {

        private final UserService userService;

        public UserController(
                        UserService userService) {

                this.userService = userService;
        }

        @PostMapping("/register")
        public ResponseEntity<UserResponse> registerUser(
                        @Valid @RequestBody UserRegistrationRequest request) {

                UserResponse response = userService.registerUser(request);

                return new ResponseEntity<>(
                                response,
                                HttpStatus.CREATED);
        }

        @GetMapping("/{userId}")
        public ResponseEntity<UserResponse> getUserById(
                        @Positive @PathVariable Long userId) {

                UserResponse response = userService.getUserById(userId);

                return ResponseEntity.ok(response);
        }

        @GetMapping
        public ResponseEntity<List<UserResponse>> getAllUsers() {

                List<UserResponse> users = userService.getAllUsers();

                return ResponseEntity.ok(users);
        }

        @PutMapping("/{userId}")
        public ResponseEntity<UserResponse> updateUser(
                        @PathVariable Long userId,
                        @Valid @RequestBody UserRegistrationRequest request) {

                UserResponse response = userService.updateUser(
                                userId,
                                request);

                return ResponseEntity.ok(response);
        }

        @DeleteMapping("/{userId}")
        public ResponseEntity<Void> deleteUser(
                        @PathVariable Long userId) {

                userService.deleteUser(userId);

                return ResponseEntity.noContent().build();
        }
}