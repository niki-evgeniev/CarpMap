package com.example.carpmap.Repository;

import com.example.carpmap.Models.Entity.IpAddress;
import com.example.carpmap.Models.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Page<User> findAllByUsernameIsNot(Pageable pageable, String username);
}
