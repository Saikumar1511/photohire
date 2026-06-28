package com.photohire.repository;

import com.photohire.entity.Booking;
import com.photohire.entity.ClientRequirement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRequirementRepository
        extends JpaRepository<ClientRequirement, Long> {

    Optional<ClientRequirement> findByBooking(Booking booking);

}