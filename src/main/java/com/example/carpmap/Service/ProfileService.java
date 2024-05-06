package com.example.carpmap.Service;

import com.example.carpmap.Models.DTO.Profile.ProfileAllDTO;
import com.example.carpmap.Models.DTO.Profile.ProfileEditDTO;
import com.example.carpmap.Models.DTO.Profile.ProfileInfoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

public interface ProfileService {

    Page<ProfileAllDTO> findAllUsers(Pageable pageable);


    ProfileInfoDTO findProfile(UserDetails userDetails);

    ProfileInfoDTO findProfileById(Long id);

    ProfileEditDTO mapInfoDtoToEditDTO(ProfileInfoDTO profileInfoDTO);

    void editUser(ProfileEditDTO profileEditDTO);
}
