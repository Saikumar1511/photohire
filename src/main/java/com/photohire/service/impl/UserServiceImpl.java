package com.photohire.service.impl;

import com.photohire.dto.request.UserRegistrationRequest;
import com.photohire.dto.response.UserResponse;
import com.photohire.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public UserResponse registerUser(
            UserRegistrationRequest request) {

        return null;
    }

    @Override
    public UserResponse getUserById(Long userId) {

        return null;
    }

    @Override
    public List<UserResponse> getAllUsers() {

        return null;
    }
}