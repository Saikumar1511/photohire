package com.photohire.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.photohire.dto.request.UserRegistrationRequest;
import com.photohire.dto.response.UserResponse;
import com.photohire.enums.UserRole;
import com.photohire.exception.ResourceAlreadyExistsException;
import com.photohire.exception.UserNotFoundException;
import com.photohire.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    private UserRegistrationRequest request;    
    private UserResponse response;

    @BeforeEach
    void setUp() {

        request = new UserRegistrationRequest();
        request.setFirstName("Sai");
        request.setLastName("Kumar");
        request.setEmail("sai@gmail.com");
        request.setPhoneNumber("9999999999");
        request.setPassword("password123");
        request.setRole(UserRole.CLIENT);

        response = new UserResponse();
        response.setId(1L);
        response.setFirstName("Sai");
        response.setLastName("Kumar");
        response.setEmail("sai@gmail.com");
        response.setPhoneNumber("9999999999");
        response.setRole(UserRole.CLIENT);
    }

    @Test
    void shouldRegisterUserSuccessfully() throws Exception {

        when(userService.registerUser(any(UserRegistrationRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("Sai"))
                .andExpect(jsonPath("$.email").value("sai@gmail.com"));

        verify(userService, times(1))
                .registerUser(any(UserRegistrationRequest.class));
    }

    @Test
    void shouldGetUserById() throws Exception {

        when(userService.getUserById(1L))
                .thenReturn(response);

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("Sai"));

        verify(userService).getUserById(1L);
    }

    @Test
    void shouldReturnNotFoundWhenUserDoesNotExist() throws Exception {

        when(userService.getUserById(1L))
                .thenThrow(new UserNotFoundException("User not found"));

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isNotFound());

        verify(userService).getUserById(1L);
    }

    @Test
    void shouldGetAllUsers() throws Exception {

        UserResponse response2 = new UserResponse();
        response2.setId(2L);
        response2.setFirstName("Rahul");
        response2.setLastName("Sharma");
        response2.setEmail("rahul@gmail.com");
        response2.setPhoneNumber("8888888888");
        response2.setRole(UserRole.PHOTOGRAPHER);

        when(userService.getAllUsers())
                .thenReturn(List.of(response, response2));

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].firstName").value("Sai"))
                .andExpect(jsonPath("$[1].firstName").value("Rahul"));

        verify(userService).getAllUsers();
    }

    @Test
    void shouldReturnConflictWhenEmailAlreadyExists() throws Exception {

        when(userService.registerUser(any(UserRegistrationRequest.class)))
                .thenThrow(new ResourceAlreadyExistsException("Email already exists"));

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict());

        verify(userService).registerUser(any(UserRegistrationRequest.class));
    }
}