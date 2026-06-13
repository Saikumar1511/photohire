package com.photohire.repository;

import com.photohire.entity.User;
import com.photohire.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByPhoneNumber(String phoneNumber);

    List<User> findByRole(UserRole role);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

}