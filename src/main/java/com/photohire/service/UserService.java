package com.photohire.service;

import com.photohire.dto.request.UserRegistrationRequest;
import com.photohire.dto.response.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse registerUser(
            UserRegistrationRequest request);

    UserResponse getUserById(Long userId);

    List<UserResponse> getAllUsers();

}