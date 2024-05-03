package com.example.carpmap.Controller;

import com.example.carpmap.Models.DTO.Profile.ProfileAllDTO;
import com.example.carpmap.Models.DTO.Profile.ProfileEditDTO;
import com.example.carpmap.Models.DTO.Profile.ProfileInfoDTO;
import com.example.carpmap.Models.DTO.Reservoirs.ReservoirsAddDTO;
import com.example.carpmap.Service.ProfileService;
import jakarta.validation.Valid;
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

    @PostMapping("details")
    public ModelAndView details(@Valid ProfileEditDTO profileEditDTO,
                                BindingResult bindingResult) {
//        String testId = id;

        if (bindingResult.hasErrors()) {
//            ModelAndView modelAndView = new ModelAndView("redirect:/profile#profile-edit");
            ModelAndView modelAndView = new ModelAndView("profile");
            modelAndView.addObject("profileEditDTO", profileEditDTO);
//            modelAndView.addObject("profileAllDTO", profileAllDTO);
            return modelAndView;
        }
        return null;
    }

    @ModelAttribute
    ProfileEditDTO profileEditDTO() {
        return new ProfileEditDTO();
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
