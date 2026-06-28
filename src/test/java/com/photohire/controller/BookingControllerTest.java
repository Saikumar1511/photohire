package com.photohire.controller;

import java.math.BigDecimal;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.photohire.dto.request.BookingRequest;
import com.photohire.dto.response.BookingResponse;
import com.photohire.enums.BookingStatus;
import com.photohire.exception.BookingNotFoundException;
import com.photohire.exception.PhotographerUnavailableException;
import com.photohire.service.BookingService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.photohire.dto.request.ClientRequirementRequest;
import com.photohire.dto.request.BookingEquipmentRequirementRequest;
import com.photohire.enums.EquipmentType;

@WebMvcTest(BookingController.class)
@AutoConfigureMockMvc(addFilters = false)
class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookingService bookingService;

    private BookingRequest request;
    private BookingResponse response;

    @BeforeEach
    void setUp() {

        request = new BookingRequest();
        request.setClientId(1L);
        request.setPhotographerId(2L);
        request.setLocation("Coorg");
        request.setStartDate(LocalDate.of(2026, 8, 1));
        request.setEndDate(LocalDate.of(2026, 8, 3));
        request.setTotalDays(3);
        request.setExpectedBudget(BigDecimal.valueOf(18000));

        ClientRequirementRequest clientRequirement = new ClientRequirementRequest();
        clientRequirement.setRequiredCameraCount(2);
        clientRequirement.setRequiredLensCount(3);
        clientRequirement.setSpecialNotes("Outdoor");

        request.setClientRequirement(clientRequirement);

        BookingEquipmentRequirementRequest equipment = new BookingEquipmentRequirementRequest();
        equipment.setEquipmentType(EquipmentType.CAMERA);
        equipment.setBrand("Canon");
        equipment.setModel("R6");
        equipment.setQuantity(2);

        request.setBookingEquipmentRequirement(equipment);

        response = new BookingResponse();
        response.setId(1L);
        response.setClientId(1L);
        response.setPhotographerId(2L);
        response.setLocation("Coorg");
        response.setStartDate(LocalDate.of(2026, 8, 1));
        response.setEndDate(LocalDate.of(2026, 8, 3));
        response.setTotalDays(3);
        response.setExpectedBudget(BigDecimal.valueOf(18000));
        response.setStatus(BookingStatus.PENDING);
    }

    @Test
    void shouldCreateBookingSuccessfully() throws Exception {

        when(bookingService.createBooking(any(BookingRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/bookings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.location").value("Coorg"))
                .andExpect(jsonPath("$.status").value("PENDING"));

        verify(bookingService, times(1))
                .createBooking(any(BookingRequest.class));
    }

    @Test
    void shouldReturnUnavailableWhenPhotographerUnavailable()
            throws Exception {

        when(bookingService.createBooking(any(BookingRequest.class)))
                .thenThrow(new PhotographerUnavailableException(
                        "Photographer unavailable"));

        mockMvc.perform(post("/api/bookings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verify(bookingService)
                .createBooking(any(BookingRequest.class));
    }

    @Test
    void shouldGetBookingById() throws Exception {

        when(bookingService.getBookingById(1L))
                .thenReturn(response);

        mockMvc.perform(get("/api/bookings/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.location").value("Coorg"));

        verify(bookingService)
                .getBookingById(1L);
    }

    @Test
    void shouldReturnNotFoundWhenBookingDoesNotExist()
            throws Exception {

        when(bookingService.getBookingById(1L))
                .thenThrow(new BookingNotFoundException(
                        "Booking not found"));

        mockMvc.perform(get("/api/bookings/1"))
                .andExpect(status().isNotFound());

        verify(bookingService)
                .getBookingById(1L);
    }

    @Test
    void shouldReturnClientBookings() throws Exception {

        when(bookingService.getClientBookings(1L))
                .thenReturn(List.of(response));

        mockMvc.perform(get("/api/bookings/client/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].clientId").value(1));

        verify(bookingService)
                .getClientBookings(1L);
    }

    @Test
    void shouldReturnPhotographerBookings() throws Exception {

        when(bookingService.getPhotographerBookings(2L))
                .thenReturn(List.of(response));

        mockMvc.perform(get("/api/bookings/photographer/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].photographerId").value(2));

        verify(bookingService)
                .getPhotographerBookings(2L);
    }

    @Test
    void shouldCancelBookingSuccessfully()
            throws Exception {

        response.setStatus(BookingStatus.CANCELLED);

        when(bookingService.cancelBooking(1L))
                .thenReturn(response);

        mockMvc.perform(put("/api/bookings/1/cancel"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status")
                        .value("CANCELLED"));

        verify(bookingService)
                .cancelBooking(1L);
    }

    @Test
    void shouldReturnNotFoundWhenCancellingInvalidBooking()
            throws Exception {

        when(bookingService.cancelBooking(1L))
                .thenThrow(new BookingNotFoundException(
                        "Booking not found"));

        mockMvc.perform(put("/api/bookings/1/cancel"))
                .andExpect(status().isNotFound());

        verify(bookingService)
                .cancelBooking(1L);
    }
}
