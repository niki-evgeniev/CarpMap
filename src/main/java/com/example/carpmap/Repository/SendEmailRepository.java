package com.example.carpmap.Repository;

import com.example.carpmap.Models.Entity.SendEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SendEmailRepository extends JpaRepository<SendEmail, Long> {
}
