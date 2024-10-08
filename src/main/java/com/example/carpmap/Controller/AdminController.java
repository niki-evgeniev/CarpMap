package com.example.carpmap.Controller;

import com.example.carpmap.Models.DTO.Profile.ProfileAllDTO;
import com.example.carpmap.Models.DTO.Profile.ProfileEditDTO;
import com.example.carpmap.Models.DTO.Profile.ProfileInfoDTO;
import com.example.carpmap.Models.DTO.Profile.ProfileNewPasswordDTO;
import com.example.carpmap.Service.ProfileService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/admin/")
public class AdminController {

    private final ProfileService profileService;


    public AdminController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("details/byId/{id}")
    public ModelAndView detailsById(@PathVariable("id") Long id) {

        String activeTab = "profile-overview";
        ProfileInfoDTO profileInfoDTO = profileService.findProfileById(id);
        ProfileEditDTO map = profileService.mapInfoDtoToEditDTO(profileInfoDTO);
        ProfileNewPasswordDTO profileNewPasswordDTO = new ProfileNewPasswordDTO();
        profileNewPasswordDTO.setId(profileInfoDTO.getId());

        ModelAndView modelAndView = new ModelAndView("profile");
        modelAndView.addObject("profileInfoDTO", profileInfoDTO);
        modelAndView.addObject("profileEditDTO", map);
        modelAndView.addObject("profileNewPasswordDTO", profileNewPasswordDTO);
        modelAndView.addObject("activeTab", activeTab);
        return modelAndView;
    }

    @GetMapping("profiles")
    public ModelAndView profiles(
            @PageableDefault(size = 9, sort = "id") Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView("adminAllProfiles");
        Page<ProfileAllDTO> allProfiles = profileService.findAllUsers(pageable);
        modelAndView.addObject("allProfiles", allProfiles);
        return modelAndView;
    }

}
