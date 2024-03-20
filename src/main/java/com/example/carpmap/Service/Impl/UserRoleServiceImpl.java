package com.example.carpmap.Service.Impl;

import com.example.carpmap.Models.Entity.UserRole;
import com.example.carpmap.Models.Enums.RoleType;
import com.example.carpmap.Repository.UserRoleRepository;
import com.example.carpmap.Service.UserRoleService;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepository userRoleRepository;

    public UserRoleServiceImpl(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public void addRoleIfNotExist() {
        if (userRoleRepository.count() == 0) {
            RoleType[] values = RoleType.values();
            for (RoleType value : values) {
                UserRole userRole = new UserRole();
                userRole.setRoleType(value);
                userRoleRepository.save(userRole);
            }

        }
    }
}
