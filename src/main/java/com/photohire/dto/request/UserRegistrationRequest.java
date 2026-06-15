package com.photohire.dto.request;

import com.photohire.enums.UserRole;
import lombok.Data;

@Data
public class UserRegistrationRequest {

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String password;

    private UserRole role;
}