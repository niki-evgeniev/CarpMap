package com.example.carpmap.Service.Impl;

import com.example.carpmap.Models.DTO.Profile.*;
import com.example.carpmap.Models.DTO.Users.ErrorRegister;
import com.example.carpmap.Models.Entity.User;
import com.example.carpmap.Models.Entity.UserRole;
import com.example.carpmap.Models.Enums.RoleType;
import com.example.carpmap.Repository.UserRepository;
import com.example.carpmap.Repository.UserRoleRepository;
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
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.example.carpmap.Cammon.ErrorMessages.*;
import static com.example.carpmap.Cammon.SuccessfulMessages.*;

@Service
public class ProfileServiceImpl implements ProfileService {

    private final Logger LOGGER = LoggerFactory.getLogger(ProfileServiceImpl.class);

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleRepository userRoleRepository;

    public ProfileServiceImpl(UserRepository userRepository, ModelMapper modelMapper,
                              PasswordEncoder passwordEncoder, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public Page<ProfileAllDTO> findAllUsers(Pageable pageable, UserDetails userDetails) {

        Page<User> allByUsernameIsNotLoggedUser = userRepository.findAllByUsernameIsNot(pageable, userDetails.getUsername());
        Page<ProfileAllDTO> allProfiles = allByUsernameIsNotLoggedUser
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
        setRoleForUser(profile, profileDTO);
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
        setRoleForUser(profile, profileDTO);
        System.out.println("SUCCESSFUL find user Profile ID");
        return profileDTO;
    }

    private static void setRoleForUser(Optional<User> profile, ProfileInfoDTO profileDTO) {

        if (profile.isPresent()) {
            if (profile.get().getRoles().size() == 3) {
                profileDTO.setRoleType(RoleType.ADMIN);
            } else if (profile.get().getRoles().size() == 2) {
                profileDTO.setRoleType(RoleType.MODERATOR);
            } else {
                profileDTO.setRoleType(RoleType.USER);
            }
        }
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
            System.out.printf(SUCCESSFUL_EDIT_USER, editUser.getUsername());
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
                    System.out.printf(SUCCESSFUL_CHANGE_PASSWORD, changeUserPassword.getUsername());

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

    @Override
    public void changeRoles(ProfileChangeRoleDTO profileChangeRoleDTO) {
        Optional<User> findUser = userRepository.findById(profileChangeRoleDTO.getId());
        if (findUser.isPresent()) {
            User userAddRole = findUser.get();
            if (profileChangeRoleDTO.getRoleType().equals(RoleType.MODERATOR)) {
                
                List<UserRole> allRoles = userRoleRepository.findAll();
                userAddRole.setRoles(List.of(allRoles.get(1), allRoles.get(2)));

                System.out.println();

            } else if (profileChangeRoleDTO.getRoleType().equals(RoleType.ADMIN)) {

                List<UserRole> roles = Stream.of(RoleType.ADMIN, RoleType.MODERATOR, RoleType.USER)
                        .map(roleType -> {
                            UserRole role = new UserRole();
                            role.setRoleType(roleType);
                            return role;
                        }).toList();
                userRoleRepository.saveAll(roles);
                userAddRole.setRoles(roles);
                System.out.println();
            }
            userRepository.save(userAddRole);
        }
    }

}
