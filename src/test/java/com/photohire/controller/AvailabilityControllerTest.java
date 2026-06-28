package com.photohire.controller;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.photohire.dto.request.AvailabilityRequest;
import com.photohire.dto.response.AvailabilityResponse;
import com.photohire.exception.PhotographerNotFoundException;
import com.photohire.service.AvailabilityService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(AvailabilityController.class)
@AutoConfigureMockMvc(addFilters = false)
class AvailabilityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AvailabilityService availabilityService;

    private AvailabilityRequest request;
    private AvailabilityResponse response;

    @BeforeEach
    void setUp() {

        request = new AvailabilityRequest();
        request.setPhotographerProfileId(1L);
        request.setAvailableDate(
                LocalDate.of(2026, 8, 1));

        response = new AvailabilityResponse();
        response.setId(1L);
        response.setPhotographerProfileId(1L);
        response.setAvailableDate(
                LocalDate.of(2026, 8, 1));
    }

    @Test
    void shouldAddAvailabilitySuccessfully()
            throws Exception {

        when(availabilityService.addAvailability(any(
                AvailabilityRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/availabilities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.photographerProfileId").value(1))
                .andExpect(jsonPath("$.availableDate")
                        .value("2026-08-01"));

        verify(availabilityService, times(1))
                .addAvailability(any(
                        AvailabilityRequest.class));
    }

    @Test
    void shouldReturnNotFoundWhenPhotographerProfileDoesNotExist()
            throws Exception {

        when(availabilityService.addAvailability(any(
                AvailabilityRequest.class)))
                .thenThrow(new PhotographerNotFoundException(
                        "Photographer profile not found"));

        mockMvc.perform(post("/api/availabilities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());

        verify(availabilityService)
                .addAvailability(any(
                        AvailabilityRequest.class));
    }

    @Test
    void shouldGetPhotographerAvailability()
            throws Exception {

        when(availabilityService
                .getPhotographerAvailability(1L))
                .thenReturn(List.of(response));

        mockMvc.perform(get("/api/availabilities/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].photographerProfileId")
                        .value(1));

        verify(availabilityService)
                .getPhotographerAvailability(1L);
    }

    @Test
    void shouldReturnNotFoundWhenGettingAvailabilityForInvalidProfile()
            throws Exception {

        when(availabilityService
                .getPhotographerAvailability(1L))
                .thenThrow(new PhotographerNotFoundException(
                        "Photographer profile not found"));

        mockMvc.perform(get("/api/availabilities/1"))
                .andExpect(status().isNotFound());

        verify(availabilityService)
                .getPhotographerAvailability(1L);
    }

    @Test
    void shouldDeleteAvailabilitySuccessfully()
            throws Exception {

        doNothing().when(availabilityService)
                .deleteAvailability(1L);

        mockMvc.perform(delete("/api/availabilities/1"))
                .andExpect(status().isNoContent());

        verify(availabilityService)
                .deleteAvailability(1L);
    }
}
