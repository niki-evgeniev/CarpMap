package com.example.carpmap.Service.Impl;

import com.example.carpmap.Models.DTO.Users.RegisterDTO;
import com.example.carpmap.Models.Entity.User;
import com.example.carpmap.Repository.UserRepository;
import com.example.carpmap.Service.UsersService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class UsersServiceImpl implements UsersService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UsersServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean registerUser(RegisterDTO registerDTO) {
        Optional<User> byUsername = userRepository.findByUsername(registerDTO.getUsername());

        if (byUsername.isEmpty() && registerDTO.getPassword().equals(registerDTO.getConfirmPassword())) {

            User userRegister = modelMapper.map(registerDTO, User.class);
            userRegister.setCreateOn(LocalDate.now());

            return true;

        }
        return false;
    }
}
