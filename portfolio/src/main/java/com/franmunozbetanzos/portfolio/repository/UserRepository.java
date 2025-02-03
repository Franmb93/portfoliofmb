package com.franmunozbetanzos.portfolio.repository;

import com.franmunozbetanzos.portfolio.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);

    boolean existsBy(String email);

    Optional<User> findByUsername(String username);
}