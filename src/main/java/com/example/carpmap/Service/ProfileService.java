package com.example.carpmap.Service;

import com.example.carpmap.Models.DTO.Profile.ProfileAllDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProfileService {
    Page<ProfileAllDTO> findAllUsers(Pageable pageable);
}
