package com.photohire.repository;

import com.photohire.entity.Booking;
import com.photohire.entity.User;
import com.photohire.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository
        extends JpaRepository<Booking, Long> {

    List<Booking> findByClient(User client);

    List<Booking> findByPhotographer(User photographer);

    List<Booking> findByStatus(BookingStatus status);


}