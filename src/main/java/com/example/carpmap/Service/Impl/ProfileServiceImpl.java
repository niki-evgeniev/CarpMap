package com.example.carpmap.Service.Impl;

import com.example.carpmap.Models.DTO.Profile.ProfileAllDTO;
import com.example.carpmap.Models.DTO.Profile.ProfileEditDTO;
import com.example.carpmap.Models.DTO.Profile.ProfileInfoDTO;
import com.example.carpmap.Models.DTO.Profile.ProfileNewPasswordDTO;
import com.example.carpmap.Models.DTO.Users.ErrorRegister;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.carpmap.Cammon.ErrorMessages.*;
import static com.example.carpmap.Cammon.SuccessfulMessages.*;

@Service
public class ProfileServiceImpl implements ProfileService {

    private final Logger LOGGER = LoggerFactory.getLogger(ProfileServiceImpl.class);

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public ProfileServiceImpl(UserRepository userRepository, ModelMapper modelMapper,
                              PasswordEncoder passwordEncoder) {
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
        System.out.printf(SUCCESSFUL_LOAD_INFORMATION_ABOUT_USERS);
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
            System.out.printf(SUCCESSFUL_EDIT_USER , editUser.getUsername());
        } else {
            System.out.printf(USERNAME_NOT_FOUND);
        }
    }

    @Override
    public List<ErrorRegister> changePassword(ProfileNewPasswordDTO profileNewPasswordDTO) {
        Optional<User> findUser = userRepository.findById(profileNewPasswordDTO.getId());
        List<ErrorRegister> err = new ArrayList<>();
        if (findUser.isPresent()) {
            String oldPassword = findUser.get().getPassword();
            String currentPassword = profileNewPasswordDTO.getCurrentPassword();
            boolean matches = passwordEncoder.matches(currentPassword, oldPassword);

            if (matches) {
                if (profileNewPasswordDTO.getNewPassword().equals(profileNewPasswordDTO.getConfirmNewPassword())) {
                    User changeUserPassword = findUser.get();
                    changeUserPassword.setPassword(passwordEncoder.encode(profileNewPasswordDTO.getNewPassword()));
                    userRepository.save(changeUserPassword);
                    System.out.printf(SUCCESSFUL_CHANGE_PASSWORD,changeUserPassword.getUsername());

                } else {
                    ErrorRegister errorRegister = new ErrorRegister();
                    errorRegister.setError(NEW_PASSWORD_NOT_MATCH);
                    System.out.printf(NEW_PASSWORD_NOT_MATCH);
                    err.add(errorRegister);
                }
            } else {
                ErrorRegister errorRegister = new ErrorRegister();
                errorRegister.setError(OLD_PASSWORD_NOT_MATCH);
                System.out.printf(OLD_PASSWORD_NOT_MATCH);
                err.add(errorRegister);
            }
        }
        return err;
    }

}
