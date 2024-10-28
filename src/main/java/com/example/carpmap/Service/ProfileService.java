package com.example.carpmap.Service;

import com.example.carpmap.Models.DTO.Profile.*;
import com.example.carpmap.Models.DTO.Users.ErrorRegister;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface ProfileService {

    Page<ProfileAllDTO> findAllUsers(Pageable pageable, UserDetails userDetails);

    ProfileInfoDTO findProfile(UserDetails userDetails);

    ProfileInfoDTO findProfileById(Long id);

    ProfileEditDTO mapInfoDtoToEditDTO(ProfileInfoDTO profileInfoDTO);

    void editUser(ProfileEditDTO profileEditDTO);

    List<ErrorRegister> changePassword(ProfileNewPasswordDTO profileNewPasswordDTO);

    void changeRoles(ProfileChangeRoleDTO profileChangeRoleDTO);
}

