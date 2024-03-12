package com.example.carpmap.Service.Impl;

import com.example.carpmap.Models.DTO.Users.RegisterDTO;
import com.example.carpmap.Models.Entity.User;
import com.example.carpmap.Models.Entity.UserRole;
import com.example.carpmap.Repository.UserRepository;
import com.example.carpmap.Repository.UserRoleRepository;
import com.example.carpmap.Service.UsersService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.example.carpmap.Cammon.Users.ERROR_REGISTER_USER;
import static com.example.carpmap.Cammon.Users.SUCCESSFUL_REGISTER_USER;


@Service
public class UsersServiceImpl implements UsersService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    private final ModelMapper modelMapper;

    public UsersServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean registerUser(RegisterDTO registerDTO) {
        String name = registerDTO.getName();
        String username = registerDTO.getUsername();
        String email = registerDTO.getEmail();

        Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty() && registerDTO.getPassword().equals(registerDTO.getConfirmPassword())) {
            User userRegister = modelMapper.map(registerDTO, User.class);
            userRegister.setCreateOn(LocalDate.now());
            List<UserRole> all = userRoleRepository.findAll();
            userRegister.setRoles(all);
            System.out.printf(SUCCESSFUL_REGISTER_USER, name, username, email);

            System.out.println();

            userRepository.save(userRegister);
            return true;
        }
        System.out.printf(ERROR_REGISTER_USER, name, username, email);
        return false;
    }
}
