package com.photohire.dto.response;

import com.photohire.enums.UserRole;
import lombok.Data;

@Data
public class UserResponse {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private UserRole role;
}