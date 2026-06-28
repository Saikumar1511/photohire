package com.photohire.repository;

import com.photohire.entity.Availability;
import com.photohire.entity.PhotographerProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AvailabilityRepository
                extends JpaRepository<Availability, Long> {

        List<Availability> findByPhotographerProfile(
                        PhotographerProfile photographerProfile);

        boolean existsByPhotographerProfileAndAvailableDate(
                        PhotographerProfile photographerProfile,
                        LocalDate availableDate);

        void deleteByPhotographerProfileAndAvailableDate(
                        PhotographerProfile photographerProfile,
                        LocalDate availableDate);
}