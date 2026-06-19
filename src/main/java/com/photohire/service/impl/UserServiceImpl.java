package com.photohire.service.impl;

import com.photohire.dto.request.UserRegistrationRequest;
import com.photohire.dto.response.UserResponse;
import com.photohire.entity.User;
import com.photohire.exception.ResourceAlreadyExistsException;
import com.photohire.exception.UserNotFoundException;
import com.photohire.mapper.UserMapper;
import com.photohire.repository.UserRepository;
import com.photohire.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(
            UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    @Override
    public UserResponse registerUser(
            UserRegistrationRequest request) {

        if (userRepository.existsByEmail(
                request.getEmail())) {

            throw new ResourceAlreadyExistsException(
                    "Email already exists");
        }

        if (userRepository.existsByPhoneNumber(
                request.getPhoneNumber())) {

            throw new ResourceAlreadyExistsException(
                    "Phone number already exists");
        }

        User user =
                UserMapper.toEntity(request);

        User savedUser =
                userRepository.save(user);

        return UserMapper.toResponse(savedUser);
    }

    @Override
    public UserResponse getUserById(
            Long userId) {

        User user =
                userRepository.findById(userId)
                        .orElseThrow(() ->
                                new UserNotFoundException(
                                        "User not found with id "
                                                + userId));

        return UserMapper.toResponse(user);
    }

    @Override
    public List<UserResponse> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(UserMapper::toResponse)
                .collect(Collectors.toList());
    }
}