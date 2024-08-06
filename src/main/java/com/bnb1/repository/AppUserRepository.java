package com.bnb1.repository;



import com.bnb1.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByEmail(String email);
     Optional<AppUser> findByUsername(String username);
    Optional<AppUser> findByEmailOrUsername(String email, String username);

}