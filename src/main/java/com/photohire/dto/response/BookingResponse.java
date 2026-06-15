package com.photohire.dto.response;

import com.photohire.enums.BookingStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingResponse {

    private Long id;

    private Long clientId;

    private Long photographerId;

    private String location;

    private LocalDate startDate;

    private LocalDate endDate;

    private BookingStatus status;
}