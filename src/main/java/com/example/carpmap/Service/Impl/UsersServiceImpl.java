package com.example.carpmap.Service.Impl;

import com.example.carpmap.Models.DTO.Users.RegisterDTO;
import com.example.carpmap.Models.Entity.User;
import com.example.carpmap.Models.Entity.UserRole;
import com.example.carpmap.Repository.UserRepository;
import com.example.carpmap.Repository.UserRoleRepository;
import com.example.carpmap.Service.UsersService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;

    public UsersServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void addAdminIfNotExist() {
        if (userRepository.count() == 0) {
            User firstAdmnUser = new User();
            firstAdmnUser.setName("adminov");
            firstAdmnUser.setCreateOn(LocalDate.now());
            firstAdmnUser.setEmail("admin@admin");
            firstAdmnUser.setPassword("734909fa01c605fa23051a67f16de8f522a2ef6969341effb1cbc4cac8d03860837749d5521cca0f654ceca0ea6f4aa2");
            firstAdmnUser.setUsername("admin");
            List<UserRole> all = getAllUserRoles();
            firstAdmnUser.setRoles(all);

            userRepository.save(firstAdmnUser);
        }
    }

    @Override
    public boolean registerUser(RegisterDTO registerDTO) {
        String name = registerDTO.getName();
        String username = registerDTO.getUsername();
        String email = registerDTO.getEmail();

        Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty() && registerDTO.getPassword().equals(registerDTO.getConfirmPassword())) {
            User userRegister = modelMapper.map(registerDTO, User.class);
            userRegister.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
            userRegister.setCreateOn(LocalDate.now());
            userRegister.setRoles(List.of(getAllUserRoles()
                    .get(2)));
            System.out.printf(SUCCESSFUL_REGISTER_USER, name, username, email);
            userRepository.save(userRegister);
            return true;
        }

        System.out.printf(ERROR_REGISTER_USER, name, username, email);
        return false;
    }

    private List<UserRole> getAllUserRoles() {
        List<UserRole> allRoles = userRoleRepository.findAll();
        return allRoles;
    }


}
