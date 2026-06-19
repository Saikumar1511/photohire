package com.photohire.repository;

import com.photohire.entity.PhotographerProfile;
import com.photohire.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PhotographerProfileRepository
        extends JpaRepository<PhotographerProfile, Long> {

    Optional<PhotographerProfile> findByUser(User user);

    List<PhotographerProfile> findByAvailableTrue();

}