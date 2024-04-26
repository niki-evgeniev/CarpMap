package com.example.carpmap.Service.Impl;

import com.example.carpmap.Models.DTO.Profile.ProfileAllDTO;
import com.example.carpmap.Models.Entity.User;
import com.example.carpmap.Models.Entity.UserRole;
import com.example.carpmap.Repository.UserRepository;
import com.example.carpmap.Service.ProfileService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileServiceImpl implements ProfileService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public ProfileServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Page<ProfileAllDTO> findAllUsers(Pageable pageable) {

        Page<User> all = userRepository.findAll(pageable);
        System.out.println();
        Page<ProfileAllDTO> allProfiles = all
                .map( profile -> {
                    ProfileAllDTO map = modelMapper.map(profile, ProfileAllDTO.class);
                    List<UserRole> roles = profile.getRoles();
                    if (roles.size() == 3){
                        map.setRole("ADMIN");
                    }else if (roles.size() == 2){
                        map.setRole("MODERATOR");
                    }else {
                        map.setRole("USER");
                    }
                    return map;
                });

        return allProfiles;
    }
}
