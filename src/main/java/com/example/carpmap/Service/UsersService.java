package com.example.carpmap.Service;

import com.example.carpmap.Models.DTO.Users.RegisterDTO;

public interface UsersService {
    boolean registerUser(RegisterDTO registerDTO);

    void addAdminIfNotExist();
}
