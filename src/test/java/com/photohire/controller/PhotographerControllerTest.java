package com.photohire.controller;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.photohire.dto.request.PhotographerProfileRequest;
import com.photohire.dto.response.PhotographerProfileResponse;
import com.photohire.exception.PhotographerNotFoundException;
import com.photohire.exception.UserNotFoundException;
import com.photohire.service.PhotographerService;

@WebMvcTest(PhotographerController.class)
@AutoConfigureMockMvc(addFilters = false)
class PhotographerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PhotographerService photographerService;

    private PhotographerProfileRequest request;
    private PhotographerProfileResponse response;

    @BeforeEach
    void setUp() {

        request = new PhotographerProfileRequest();
        request.setUserId(1L);
        request.setCity("Bangalore");
        request.setBio("Wedding Photographer");
        request.setDailyRate(BigDecimal.valueOf(5000));
        request.setYearsOfExperience(5);
        request.setAvailable(true);

        response = new PhotographerProfileResponse();
        response.setId(1L);
        response.setCity("Bangalore");
        response.setBio("Wedding Photographer");
        response.setDailyRate(BigDecimal.valueOf(5000));
        response.setYearsOfExperience(5);
        response.setAvailable(true);
    }

    @Test
    void shouldCreatePhotographerProfileSuccessfully()
            throws Exception {

        when(photographerService.createProfile(any(
                PhotographerProfileRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/photographers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.city").value("Bangalore"))
                .andExpect(jsonPath("$.bio").value("Wedding Photographer"));

        verify(photographerService, times(1))
                .createProfile(any(
                        PhotographerProfileRequest.class));
    }

    @Test
    void shouldReturnNotFoundWhenUserDoesNotExist()
            throws Exception {

        when(photographerService.createProfile(any(
                PhotographerProfileRequest.class)))
                .thenThrow(new UserNotFoundException(
                        "User not found"));

        mockMvc.perform(post("/api/photographers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());

        verify(photographerService)
                .createProfile(any(
                        PhotographerProfileRequest.class));
    }

    @Test
    void shouldGetPhotographerProfileById()
            throws Exception {

        when(photographerService.getProfileById(1L))
                .thenReturn(response);

        mockMvc.perform(get("/api/photographers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.city").value("Bangalore"));

        verify(photographerService)
                .getProfileById(1L);
    }

    @Test
    void shouldReturnNotFoundWhenProfileDoesNotExist()
            throws Exception {

        when(photographerService.getProfileById(1L))
                .thenThrow(new PhotographerNotFoundException(
                        "Photographer profile not found"));

        mockMvc.perform(get("/api/photographers/1"))
                .andExpect(status().isNotFound());

        verify(photographerService)
                .getProfileById(1L);
    }

    @Test
    void shouldUpdatePhotographerProfile()
            throws Exception {

        when(photographerService.updateProfile(
                eq(1L),
                any(PhotographerProfileRequest.class)))
                .thenReturn(response);

        mockMvc.perform(put("/api/photographers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.city").value("Bangalore"));

        verify(photographerService)
                .updateProfile(
                        eq(1L),
                        any(PhotographerProfileRequest.class));
    }

    @Test
    void shouldReturnAllPhotographers()
            throws Exception {

        PhotographerProfileResponse response2 =
                new PhotographerProfileResponse();

        response2.setId(2L);
        response2.setCity("Hyderabad");
        response2.setBio("Fashion Photographer");
        response2.setDailyRate(BigDecimal.valueOf(7000));
        response2.setYearsOfExperience(8);
        response2.setAvailable(true);

        when(photographerService.getAllPhotographers())
                .thenReturn(List.of(response, response2));

        mockMvc.perform(get("/api/photographers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].city").value("Bangalore"))
                .andExpect(jsonPath("$[1].city").value("Hyderabad"));

        verify(photographerService)
                .getAllPhotographers();
    }

    @Test
    void shouldReturnAvailablePhotographers()
            throws Exception {

        when(photographerService.getAvailablePhotographers())
                .thenReturn(List.of(response));

        mockMvc.perform(get("/api/photographers/available"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].available").value(true));

        verify(photographerService)
                .getAvailablePhotographers();
    }
}
