package com.example.carpmap.Service;

import com.example.carpmap.Models.DTO.Profile.ProfileAllDTO;
import com.example.carpmap.Models.DTO.Users.ErrorRegister;
import com.example.carpmap.Models.DTO.Users.RegisterDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UsersService {

    void addAdminIfNotExist();

    List<ErrorRegister> registerNewUser(RegisterDTO registerDTO);
}
