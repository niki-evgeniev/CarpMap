package com.example.carpmap.Controller;

import com.example.carpmap.Models.DTO.Profile.ProfileAllDTO;
import com.example.carpmap.Models.DTO.Profile.ProfileEditDTO;
import com.example.carpmap.Models.DTO.Profile.ProfileInfoDTO;
import com.example.carpmap.Models.DTO.Profile.ProfileNewPasswordDTO;
import com.example.carpmap.Models.DTO.Users.ErrorRegister;
import com.example.carpmap.Service.ProfileService;
import jakarta.servlet.http.HttpServletRequest;
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

import java.util.List;

@Controller
@RequestMapping("/profile/")
public class ProfileController {

    private final String OVERVIEW = "profile-overview";
    private final String EDIT = "profile-edit";
    private final String SETTINGS = "profile-settings";
    private final String CHANGE_PASSWORD = "profile-change-password";
    private final String CHANGE_ROLE = "profile-change-role";
    private final ProfileService profileService;
    private final HttpServletRequest request;

    public ProfileController(ProfileService profileService, HttpServletRequest request) {
        this.profileService = profileService;
        this.request = request;
    }

    @GetMapping("details")
    public ModelAndView details(@RequestParam(value = "activeTab", required = false) String activeTab,
                                @AuthenticationPrincipal UserDetails userDetails) {
//        PROFILE USER
        String url = request.getRequestURI();
        ModelAndView modelAndView = getAllView(userDetails);
        modelAndView.addObject("url", url);
        modelAndView.addObject("activeTab", OVERVIEW);
        return modelAndView;
    }

    @PostMapping("details/{id}")
    public ModelAndView details(@PathVariable("id") Long id,
                                @Valid ProfileEditDTO profileEditDTO,
                                BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("profile");
        String url = request.getRequestURI();
        modelAndView.addObject("url", url);
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("profileInfoDTO", profileService.findProfileById(id));
            modelAndView.addObject("activeTab", EDIT);
            return modelAndView;
        }
        profileService.editUser(profileEditDTO);
        getEditDTOAndNewPasswordDTO(modelAndView, profileService.findProfileById(id), profileEditDTO.getId());
        modelAndView.addObject("activeTab", OVERVIEW);
        return modelAndView;
    }

    @PostMapping("details/newPassword/{id}")
    public ModelAndView newPassword(@PathVariable("id") Long id,
                                    @Valid ProfileNewPasswordDTO profileNewPasswordDTO,
                                    BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("profile");
        String url = request.getRequestURI();
        modelAndView.addObject("url", url);
        modelAndView.addObject("profileInfoDTO", profileService.findProfileById(id));

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("activeTab", CHANGE_PASSWORD);
            return modelAndView;
        }

        List<ErrorRegister> err = profileService.changePassword(profileNewPasswordDTO);

        if (err.isEmpty()) {
//            modelAndView.addObject("activeTab", OVERVIEW);
//            modelAndView.addObject("profileInfoDTO", profileService.findProfileById(id));
            return new ModelAndView("redirect:/profile/details");
        }
        modelAndView.addObject("activeTab", CHANGE_PASSWORD);
        modelAndView.addObject("errors", err);

        return modelAndView;
    }

    @ModelAttribute
    ProfileNewPasswordDTO profileNewPasswordDTO() {
        return new ProfileNewPasswordDTO();
    }

    @ModelAttribute
    ProfileEditDTO profileEditDTO() {
        return new ProfileEditDTO();
    }


    private ModelAndView getAllView(UserDetails userDetails) {
        ModelAndView modelAndView = new ModelAndView("profile");
        ProfileInfoDTO profileInfoDTO = profileService.findProfile(userDetails);
        getEditDTOAndNewPasswordDTO(modelAndView, profileInfoDTO, profileInfoDTO.getId());
        ProfileEditDTO map = profileService.mapInfoDtoToEditDTO(profileInfoDTO);
        modelAndView.addObject("profileEditDTO", map);
        return modelAndView;
    }

    private void getEditDTOAndNewPasswordDTO(ModelAndView modelAndView, ProfileInfoDTO profileService,
                                             Long profileEditDTO) {
        modelAndView.addObject("profileInfoDTO", profileService);
        ProfileNewPasswordDTO profileNewPasswordDTO = new ProfileNewPasswordDTO();
        profileNewPasswordDTO.setId(profileEditDTO);
        modelAndView.addObject("profileNewPasswordDTO", profileNewPasswordDTO);
    }

}
