package com.example.carpmap.Service;

import com.example.carpmap.Models.DTO.Users.ErrorRegister;
import com.example.carpmap.Models.DTO.Users.RegisterDTO;

import java.util.List;

public interface UsersService {

    void addAdminIfNotExist();

    List<ErrorRegister> registerNewUser(RegisterDTO registerDTO);

}
