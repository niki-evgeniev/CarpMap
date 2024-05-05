package com.example.carpmap.Controller;

import com.example.carpmap.Models.DTO.Profile.ProfileAllDTO;
import com.example.carpmap.Models.DTO.Profile.ProfileEditDTO;
import com.example.carpmap.Models.DTO.Profile.ProfileInfoDTO;
import com.example.carpmap.Models.DTO.Reservoirs.ReservoirsAddDTO;
import com.example.carpmap.Service.ProfileService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/profile/")
public class ProfileController {

    private final String OVERVIEW = "profile-overview";
    private final String EDIT = "profile-edit";
    private final String SETTINGS = "profile-settings";
    private final String CHANGE_PASSWORD = "profile-change-password";
    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;

    }

    @GetMapping("details")
    public ModelAndView details(@RequestParam(value = "activeTab", required = false)
                                String activeTab,
                                @AuthenticationPrincipal UserDetails userDetails) {
//        PROFILE USER
        ProfileInfoDTO profileInfoDTO = profileService.findProfile(userDetails);
        ProfileEditDTO map = profileService.mapInfoDtoToEditDTO(profileInfoDTO);
        ModelAndView modelAndView = new ModelAndView("profile");
        modelAndView.addObject("profileInfoDTO", profileInfoDTO);
        modelAndView.addObject("profileEditDTO", map);
        modelAndView.addObject("activeTab", OVERVIEW);
        return modelAndView;
    }

    @PostMapping("details/{id}")
    public ModelAndView details(@PathVariable("id") Long id,
                                @Valid ProfileEditDTO profileEditDTO,
                                BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("profile");
        ProfileInfoDTO profileInfoDTO = profileService.findProfileById(id);
        ProfileEditDTO map = profileService.mapInfoDtoToEditDTO(profileInfoDTO);
        modelAndView.addObject("profileInfoDTO", profileInfoDTO);
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("activeTab", EDIT);
            return modelAndView;
        }
        modelAndView.addObject("activeTab", OVERVIEW);
        return modelAndView;
    }

    @ModelAttribute
    ProfileEditDTO profileEditDTO() {
        return new ProfileEditDTO();
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
