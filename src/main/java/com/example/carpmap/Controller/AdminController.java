package com.example.carpmap.Controller;

import com.example.carpmap.Models.DTO.Profile.ProfileInfoDTO;
import com.example.carpmap.Service.ProfileService;
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
        ProfileInfoDTO profileInfoDTO = profileService.findProfileById(id);
        ModelAndView modelAndView = new ModelAndView("profile");
        modelAndView.addObject("profileInfoDTO", profileInfoDTO);
        return modelAndView;
    }

}
