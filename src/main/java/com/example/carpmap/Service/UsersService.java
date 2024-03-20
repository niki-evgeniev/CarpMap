package com.example.carpmap.Service;

import com.example.carpmap.Models.DTO.Users.RegisterDTO;

public interface UsersService {

    void addAdminIfNotExist();

    boolean registerUser(RegisterDTO registerDTO);

}
