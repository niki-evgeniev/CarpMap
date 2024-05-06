package com.example.carpmap.Service.Impl;

import com.example.carpmap.Models.DTO.Profile.ProfileAllDTO;
import com.example.carpmap.Models.DTO.Profile.ProfileEditDTO;
import com.example.carpmap.Models.DTO.Profile.ProfileInfoDTO;
import com.example.carpmap.Models.DTO.Profile.ProfileNewPasswordDTO;
import com.example.carpmap.Models.Entity.User;
import com.example.carpmap.Models.Entity.UserRole;
import com.example.carpmap.Repository.UserRepository;
import com.example.carpmap.Service.ProfileService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.example.carpmap.Cammon.SuccessfulMessages.SUCCESSFUL_FIND_PROFILE;

@Service
public class ProfileServiceImpl implements ProfileService {

    private final Logger LOGGER = LoggerFactory.getLogger(ProfileServiceImpl.class);

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public ProfileServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Page<ProfileAllDTO> findAllUsers(Pageable pageable) {

        Page<User> all = userRepository.findAll(pageable);
        Page<ProfileAllDTO> allProfiles = all
                .map(profile -> {
                    ProfileAllDTO map = modelMapper.map(profile, ProfileAllDTO.class);
                    List<UserRole> roles = profile.getRoles();
                    if (roles.size() == 3) {
                        map.setRole("ADMIN");
                    } else if (roles.size() == 2) {
                        map.setRole("MODERATOR");
                    } else {
                        map.setRole("USER");
                    }
                    return map;
                });
        System.out.println("SUCCESSFUL load Information about users");
        return allProfiles;
    }

    @Override
    public ProfileInfoDTO findProfile(UserDetails userDetails) {
        Optional<User> profile = userRepository.findByUsername(userDetails.getUsername());

        if (profile.isEmpty()) {
            String errorMessage = String.format("User not found: %s", userDetails.getUsername());
            LOGGER.error(errorMessage);
            throw new UsernameNotFoundException(errorMessage);
        }
        ProfileInfoDTO profileDTO = modelMapper.map(profile, ProfileInfoDTO.class);
        String format = String.format(SUCCESSFUL_FIND_PROFILE);
        LOGGER.info(format);
        return profileDTO;
    }

    @Override
    public ProfileInfoDTO findProfileById(Long id) {
        Optional<User> profile = userRepository.findById(id);
        if (profile.isEmpty()) {
            String errorMessage = String.format("User not found with id %s", id);
            LOGGER.error(errorMessage);
        }
        ProfileInfoDTO profileDTO = modelMapper.map(profile, ProfileInfoDTO.class);
        System.out.println("SUCCESSFUL find user Profile ID");
        return profileDTO;
    }

    @Override
    public ProfileEditDTO mapInfoDtoToEditDTO(ProfileInfoDTO profileInfoDTO) {
        ProfileEditDTO map = modelMapper.map(profileInfoDTO, ProfileEditDTO.class);
        map.setPhoneNumber(profileInfoDTO.getPhone());
        return map;
    }

    @Override
    public void editUser(ProfileEditDTO profileEditDTO) {
        Optional<User> user = userRepository.findById(profileEditDTO.getId());
        if (user.isPresent()) {
            User editUser = modelMapper.map(profileEditDTO, User.class);
            editUser.setUsername(user.get().getUsername());
            editUser.setPassword(user.get().getPassword());
            editUser.setCreateOn(user.get().getCreateOn());
            editUser.setModified(LocalDateTime.now());
            editUser.setRoles(user.get().getRoles());
            editUser.setModified(LocalDateTime.now());
            userRepository.save(editUser);
            System.out.println("SUCCESSFUL edit username: " + editUser.getUsername());
        } else {
            System.out.println("USERNAME to edit is not found");
        }
    }

    @Override
    public void changePassword(ProfileNewPasswordDTO profileNewPasswordDTO) {
        Optional<User> findUser = userRepository.findById(profileNewPasswordDTO.getId());
        if (findUser.isPresent()) {
            String password = findUser.get().getPassword();
            String currentPassword = profileNewPasswordDTO.getCurrentPassword();
            boolean matches = passwordEncoder.matches(currentPassword, password );
            if (matches & profileNewPasswordDTO.getNewPassword().equals(profileNewPasswordDTO.getConfirmNewPassword())) {
                User map = modelMapper.map(findUser, User.class);
                map.setPassword(passwordEncoder.encode(profileNewPasswordDTO.getNewPassword()));
                System.out.println();
            }
        }
    }

}
