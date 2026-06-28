package com.photohire.repository;

import com.photohire.entity.Booking;
import com.photohire.entity.BookingEquipmentRequirement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingEquipmentRequirementRepository
        extends JpaRepository<BookingEquipmentRequirement, Long> {

    List<BookingEquipmentRequirement> findByBooking(Booking booking);

}