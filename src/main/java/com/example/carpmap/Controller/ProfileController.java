package com.example.carpmap.Controller;

import com.example.carpmap.Models.DTO.Profile.ProfileAllDTO;
import com.example.carpmap.Models.DTO.Profile.ProfileInfoDTO;
import com.example.carpmap.Service.ProfileService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/profile/")
public class ProfileController {
    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }


    @GetMapping("details")
    public ModelAndView details(
            @AuthenticationPrincipal UserDetails userDetails) {
//        PROFILE USER
        ProfileInfoDTO profileInfoDTO = profileService.findProfile(userDetails);
        ModelAndView modelAndView = new ModelAndView("profile");
        modelAndView.addObject("profileInfoDTO", profileInfoDTO);
        return modelAndView;
    }


    @GetMapping("profiles")
    public ModelAndView profiles(
            @PageableDefault(size = 9, sort = "firstName") Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView("adminAllProfiles");
        Page<ProfileAllDTO> allProfiles = profileService.findAllUsers(pageable);
        modelAndView.addObject("allProfiles", allProfiles);
        return modelAndView;
    }

}
